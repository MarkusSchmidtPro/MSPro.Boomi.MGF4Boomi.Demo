package processScript.utils

/// *************** COPY AND PASTE FROM HERE *****************

import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "psgUrlBuilder"

/**
 * Takes the [DPP_PathTemplate] and replaces all placeholder tags {xxx} 
 * with the properly url encoded values of a dynamic document or process property.  
 *
 * Parameters: 
 *  IN: DPP_PathTemplate   (dynamic PROCESS property)
 *      DPP_QueryTemplate  (dynamic PROCESS property)
 *          Path and query templates contain {cxx} placeholder tags.
 *
 *      DPP_ValuePropertyName   [optional] Default: DDP_Url
 *          The name of the dynamic document property that gets the value
 *          of the the replacement.
 *  
 *  The values for the query template are URL encoded (URLEncoder.encode()),
 *  while the path encoding - which is neccessary for SharePoint Urls - is 
 *  hand-crafted (_myPathEncoder()) because it requires a different encoding 
 *  and I haven't found a standard encoder.
 *  
 *  [DPP_...] tags are looked up in dynamic process properties.
 *  All other tags are considered to be dynamic document properties.
 *  If Query template does not start with ? the char is added.
 *
 * Returns: 
 *  The url path in [DDP_Url] - or in a user-defined (DPP_ValuePropertyName) property.
 * ------------------------------------------------------------------------
 * 2024-04-11      msc -   Comments adjusted
 * 2024-04-09      msc -   Fixed description
 * 2024-03-27      msc -   Path encoding added
 * 2024-03-22      msc -   Created
 */
final String userDefinedPropertyBase = 'document.dynamic.userdefined.'


_logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

final String DPP_Name_PathTemplate  = "DPP_PathTemplate"
final String DPP_Name_QueryTemplate = "DPP_QueryTemplate"
final String DPP_Name_ValueProperty = "DPP_ValuePropertyName"

try {
    //
    // Get process properties before we start with the documents
    //
    final String valuePropertyName = _getDPP(DPP_Name_ValueProperty, "DDP_Url")
    final String pathTemplate = _getDPP(DPP_Name_PathTemplate, "")
    final String queryTemplate = _getDPP(DPP_Name_QueryTemplate, "")

    _logger.fine( "${DPP_Name_PathTemplate}='${pathTemplate}'" )
    _logger.fine( "${DPP_Name_QueryTemplate}='${queryTemplate}'" )
    _logger.fine( "ValuePropertyName='${valuePropertyName}'" )

    if( pathTemplate.startsWith('/'))
        _logger.warning("The path template ${DPP_Name_PathTemplate} sould not start with /, because this may lead to 404!")

//    List<String> tags = template.findAll("\\{(.*?)\\}")
//    List<String> tags = 

    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        Properties docProperties = dataContext.getProperties(docNo) as Properties
        InputStream documentStream = dataContext.getStream(docNo)

        // ------------------------------------------------------------------------
        // This URLEncoder.encode method converts the string 
        // into application/x-www-form-urlencoded format.
        // Which can be used for query parameters, only. 
        // Not suitable for path encoding
        // --------------------------------------------------------

        String path = pathTemplate
        List<String> tags = path.findAll( "\\{(.*?)\\}")
        for( String tag in tags){
            path = path.replace( tag,
                    _myPathEncoder( _getValue(  docProperties, tag)))
        }
        // org.apache.commons.httpclient.util.URIUtil.encodePath
        _logger.fine( "Path='${path}'" )


        String query = queryTemplate
        tags = query.findAll( "\\{(.*?)\\}")
        for( String tag in tags){
            query = query.replace( tag,
                    URLEncoder.encode( _getValue(  docProperties, tag),  "UTF-8"))
        }
        _logger.fine( "Query='${query}'" )

        // Some supporting fixes
        if( query != null && query.length()>0 ) {
            if( query[0]!= '?') query = '?' + query
            if( path.endsWith('/')) path = path.substring(0, path.length()-1)
        }

        String result = path + query

        _logger.fine( "${valuePropertyName}='${result}'" )
        docProperties.setProperty( userDefinedPropertyBase + valuePropertyName, result)

        // ------------------------------------------------------------------------
        dataContext.storeStream(documentStream, docProperties)
    }
    _logger.info('<<< End Script')
}
catch (Exception e) {
    _logger.severe(e.message)
    throw e
}

// =================================================
// -------------------- LOCALS ---------------------
// =================================================

/** Get a dynamic process property.
 *
 * @param propertyName
 * @param default       If default is null, an exception is thrown when the property is not set.
 *                      Otherwise, the default is returned in case it does not exist (is empty).
 *                      Whitespaces are valid characters and a whitespaces string is not empty!
 * @return              The property value (or default)
 */
static String _getDPP( String propertyName, String defaultValue=null ) {
    String v = ExecutionUtil.getDynamicProcessProperty( propertyName)
    if( v == null || v.isEmpty()) {
        // Default handler
        if( defaultValue == null) throw new Exception( "Mandatory ${propertyName} not set.")
        v = defaultValue
    }
    return v
}

static String _getDDP( Properties docProperties, String propertyName, String defaultValue=null ) {
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
    String v = docProperties.getProperty(userDefinedPropertyBase + propertyName)
    if( v == null || v.isEmpty()) {
        // Default handler
        if( defaultValue == null) throw new Exception( "Mandatory ${propertyName} not set.")
        v = defaultValue
    }
    return v
}

private static String _getValue( Properties docProperties, String tag) {
    String propertyName = tag.substring(1, tag.length() - 1)
    return propertyName.startsWith("DPP_")
            ? _getDPP(propertyName, null)
            : _getDDP(docProperties, propertyName, null)
}

/** The is no out-of-the-box ecoder for a path of an Url.
 *  This is the handcrafted version.
 */
String _myPathEncoder(String path) {
    return path.replace( ' ', "%20")
}