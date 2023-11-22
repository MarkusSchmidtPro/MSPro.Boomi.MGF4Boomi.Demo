package Freemarker

import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// Create your Configuration instance, and specify if up to what FreeMarker
// version (here 2.3.32) do you want to apply the fixes that are not 100%
// backward-compatible. See the Configuration JavaDoc for details.
Configuration cfg = new Configuration(Configuration.VERSION_2_3_32)

// Specify the source where the template files come from. Here I set a
// plain directory for it, but non-file-system sources are possible too:
cfg.setDirectoryForTemplateLoading(new File("../../TestData/FM_Templates"))

// From here we will set the settings recommended for new projects. These
// aren't the defaults for backward compatibilty.

// Set the preferred charset template files are stored in. UTF-8 is
// a good choice in most applications:
cfg.setDefaultEncoding("UTF-8")

// Sets how errors will appear.
// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
//cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER)

// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
cfg.setLogTemplateExceptions(false)

// Wrap unchecked exceptions thrown during template processing into TemplateException-s:
cfg.setWrapUncheckedExceptions(true)

// Do not fall back to higher scopes when reading a null loop variable:
cfg.setFallbackOnNullLoopVariable(false)

// To accomodate to how JDBC returns values; see Javadoc!
cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault())



// Create the root hash. We use a Map here, but it could be a JavaBean too.
Map<String, Object> root = new HashMap<>()

// Put string "user" into the root
root.put("user", "Big Joe")

// Create the "latestProduct" hash. We use a JavaBean here, but it could be a Map too.
Product latest = new Product()
latest.setUrl("products/greenmouse.html")
latest.setName("green mouse")
// and put it into the root
root.put("latestProduct", latest)

// https://freemarker.apache.org/docs/pgui_quickstart_gettemplate.html
Template t1 = cfg.getTemplate("t1.html")
Writer out = new OutputStreamWriter(System.out)
t1.process(root, out)
println( out)


String jsonS= """
{
    "user": "Big Joe",
    "NullName": null,
    "product": {
        "Categories": [
            {
                "ID": 1,
                "active": true,
                "Name": "Cat01"
            },
            {
                "ID": 2,
                "active": false,
                "Name": "Cat02"
            },
            {
                "ID": 3,
                "active": true,
                "Name": "Cat03"
              
            }
        ],
        "Name": "green mouse",
        "Url": "products/greenmouse.html",
        "user": "Markus"
    }, "li" : [4,5,6]
}
"""

Template jT2 = cfg.getTemplate("jT2.html")
Writer out2 = new OutputStreamWriter(System.out)
final js = new JsonSlurper()
final json = js.parseText(jsonS) 
jT2.process(json, out2)
println( out2)

/**
 * Product bean; note that it must be a public class!
 */
class Product {

    private String url
    private String name

    // As per the JavaBeans spec., this defines the "url" bean property
    // It must be public!
    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    // As per the JavaBean spec., this defines the "name" bean property
    // It must be public!
    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

}