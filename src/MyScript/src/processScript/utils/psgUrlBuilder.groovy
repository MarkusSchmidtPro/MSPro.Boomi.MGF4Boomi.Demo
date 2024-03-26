package processScript.utils

/// *************** COPY AND PASTE FROM HERE *****************

import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "psgUrlBuilder"

/**
 * Takes the [DDP_UrlTemplate] and replaces all {Dxx_} tags with the url 
 * encoded values of a dynamic document or process property.  
 *
 * Parameters: 
 *  IN: DPP_PathTemplate   (dynamic PROCESS property)
 *      DPP_QueryTemplate  (dynamic PROCESS property)
 *          Path and query templates containing {Dxx} tags.
 *          The values for the Query template are URL encoded.
 *          [DPP_...] tags are looked up in dynamic process properties.
 *          All other tags are considered to be dynamic document properties.
 *          If Query template does not start with ? the char is added.
 *
 *      DPP_ValueProperty   [optional] Default: DDP_Url
 *          The name of the dynamic document property that gets the value
 *          of the the replacement.
 *  ...
 *
 * Returns: 
 *  DDP_Value
 * ------------------------------------------------------------------------
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
        
        String path = pathTemplate
        List<String> tags = path.findAll( "\\{(.*?)\\}")
        for( String tag in tags){
            path = path.replace( tag, _getValue(  docProperties, tag))
        }
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