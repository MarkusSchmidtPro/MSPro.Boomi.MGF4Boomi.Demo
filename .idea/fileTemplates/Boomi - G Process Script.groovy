package ProcessScript

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

final String SCRIPT_NAME = "${FILE_NAME}"

/* **************************************************************************
    ${SingleLineDescription}
    ------------------------------------------------
    ${DATE}  ${Author} -   Created
************************************************************************** */
final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

// Use the slurpers to parse JSON or XML documents
final JsonSlurper js = new JsonSlurper()
// final XmlSlurper  xs = new XmlSlurper()

try {
    //
    // Script Initialization
    //
    _logger.fine('In-Document Count= ' + dataContext.getDataCount())
    
    //
    // Document processing loop
    //
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        String document = _getTextDocument(dataContext, docNo)
        Properties props = dataContext.getProperties(docNo)

        // XML or JSON input document
        def jDoc = js.parseText(document) 
        
        // *********** Script functionality ************

        // your code here...
        // SAMPLE functionality
        // get contact_name from JSON document
        // get document and process proprty as set in the Test file 
        String contactName = jDoc.contact_name  
        String ddp_newContactName = _getDDP( props,"DDP_NewName", null)
        _logger.fine( "Replacing contact_name='" + contactName +" by " + ddp_newContactName )
        jDoc.contact_name = ddp_newContactName
        
        // Demo process property
        String dpp = _getDPP("DPP_ProcProp", null)
        _logger.fine( "DPP_ProcProp='" + dpp +"'" )
        
        // ******** end of Script functionality ********
        
        // generate output documents
        _setTextDocument(dataContext, JsonOutput.toJson(jDoc), props)
    }
}
catch (Exception e) {
    _logger.severe(e.message)
    throw e
}

// =================================================
// -------------------- LOCALS ---------------------
// =================================================

static String _getTextDocument(dataContext, int docNo) {
    InputStream documentStream = dataContext.getStream(docNo)
    return documentStream.getText("UTF-8")
}

static void _setTextDocument(dataContext, String value, Properties props) {
    InputStream newDocumentStream = new ByteArrayInputStream(value.getBytes("UTF-8"))
    dataContext.storeStream(newDocumentStream, props)
}


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
        if( defaultValue == null) throw new Exception( "Mandatory " + propertyName + " not set.")
        v = defaultValue
    }
    return v
}

static String _getDDP( Properties docProperties, String propertyName, String defaultValue=null ) {
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
    String v = docProperties.getProperty(userDefinedPropertyBase + propertyName)
    if( v == null || v.isEmpty()) {
        // Default handler
        if( defaultValue == null) throw new Exception( "Mandatory " + propertyName + " not set.")
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