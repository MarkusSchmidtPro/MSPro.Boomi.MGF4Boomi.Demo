package processScript.jsonArrayToDocs

import com.boomi.document.scripting.DataContext
import msPro.mgf4boomi.tests.TestHelper
import groovy.json.JsonSlurper
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

/**
 * TEST JsonArrayToDocs Script - simple test with build in documents.
 *
 */
@TypeChecked
class JsonArrayToDocs_Test01 {

    final ProcessScript _testScript = new ProcessScript('PSGJsonArrayToDocs.groovy')

    /**
     * Test split single document with some Array items, using "(root)".
     */
    @Test
    void test01() {

        // Define some test data inline
        final String jsonDoc1 = """
        [
          {
            "plz": 47441,
            "city": "Moers",
            "lattitude": 51.4463,
            "longitude": 6.6396
          },
          {
            "plz": 47798,
            "city": "Krefeld",
            "lattitude": 51.3311,
            "longitude": 6.5616
          },
          {
            "plz": 47051,
            "city": "Duisburg",
            "lattitude": 51.3311,
            "longitude": 6.5616
          }
        ]
    """

        // One document with inline test data (fromText)
        def dc = DataContext.create([
                Document.fromText(jsonDoc1)
        ])

        _testScript.run(dc)
        assert dc.Documents.size() == 3, "Three array elements should result in three documents!"
        TestHelper.printJsonDocuments(dc)
    }


    /**
     * Test split two documents with some Array items, using "MyArray".
     */
    @Test
    void test02() {

        // Defined the DPP that is used inside of the script
        // to name the Array element
        def ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_JsonArrayName = "MyArray"

        // Two JSON document with three array elements in total 
        // under the MyArray element
        def dc = DataContext.create([
                Document.fromText("""
                { "MyArray" : [
                    { "plz": 47441, "city": "Moers" },
                    { "plz": 47798, "city": "Krefeld"}
                  ]
                }"""),

                Document.fromText("""
                { "MyArray" : [
                    { "plz": 47051, "city": "Duisburg" }
                  ]
                }""")
        ])
        _testScript.run(dc, ec)
        assert dc.Documents.size() == 3, "Three array elements should result in three documents!"

        final slurper = new JsonSlurper()
        final json2 = (Map) slurper.parseText(dc.Documents[2].toString())
        
        assert json2.city == "Duisburg", "City should be Duisburg"
        TestHelper.printJsonDocuments(dc)
    }
}
