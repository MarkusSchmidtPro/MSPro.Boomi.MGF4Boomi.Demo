package processScript.businessRule

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "brResultToString"

/* **************************************************************************
    Convert a BusinessRule error message (XML) into a user readable string.
    
    IN:     DDP_business_rule_result (XML)
    
    OUT:    DDP_business_rule_result (string)
    
    ------------------------------------------------
    17.04.2024  msc -   Created
************************************************************************** */
final String DDP_PROP_NAME = "DDP_business_rule_result"

// Pattern to extract a business rule failure from any text.
// final String brPattern = "\\<business_rule_failures\\>.*\\<\\/business_rule_failures\\>"
//      final Matcher brXml = childPattern.matcher( ddpErrorMessage)
//      if( brXml.find()){
//          ddpErrorMessage = _businessRuleToString(brXml.group(0) )


final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

// Use the slurpers to parse JSON or XML documents
//final JsonSlurper js = new JsonSlurper()
// final XmlSlurper  xs = new XmlSlurper()

try {
    //
    // Script Initialization
    //
    _logger.fine("In-Document Count= " + dataContext.getDataCount())

    //
    // Document processing loop
    //
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        String document = _getTextDocument(dataContext, docNo)
        Properties props = dataContext.getProperties(docNo)

        //def jDoc = js.parseText(document)

        // *********** Script functionality ************

        String ddpErrorMessage = _getDDP( props, DDP_PROP_NAME)
        ddpErrorMessage = _businessRuleToString( ddpErrorMessage )
        _setDDP( props, DDP_PROP_NAME, ddpErrorMessage)
        _logger.fine("${DDP_PROP_NAME}='${ddpErrorMessage}'")

        // ******** end of Script functionality ********

        _setTextDocument(dataContext, document, props)
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
 * @param default If default is null, an exception is thrown when the property is not set.
 *                      Otherwise, the default is returned in case it does not exist (is empty).
 *                      Whitespaces are valid characters and a whitespaces string is not empty!
 * @return The property value (or default)
 */
static String _getDPP(String propertyName, String defaultValue = null) {
    String v = ExecutionUtil.getDynamicProcessProperty(propertyName)
    if (v == null || v.isEmpty()) {
        // Default handler
        if (defaultValue == null) throw new Exception("Mandatory " + propertyName + " not set.")
        v = defaultValue
    }
    return v
}

static String _getDDP(Properties docProperties, String propertyName, String defaultValue = null) {
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
    String v = docProperties.getProperty(userDefinedPropertyBase + propertyName)
    if (v == null || v.isEmpty()) {
        // Default handler
        if (defaultValue == null) throw new Exception("Mandatory " + propertyName + " not set.")
        v = defaultValue
    }
    return v
}

static String _setDDP(Properties docProperties, String propertyName, String value) {
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
    docProperties.setProperty(userDefinedPropertyBase + propertyName, value)
}

private static String _businessRuleToString(String businessRuleXml) {
    /*  businessRuleXml=
         <business_rule_failures>
             <business_rule_failure rule="Validate PLZ">null ist nicht befüllt</business_rule_failure>
         </business_rule_failures>
    */
    String result = ""
    def business_rule_failures = new XmlSlurper().parseText(  businessRuleXml)

    for( def nBusiness_rule_failure in business_rule_failures.business_rule_failure){
        String rule = nBusiness_rule_failure.@'rule'
        String message = nBusiness_rule_failure
        result += "${rule}: ${message}\r\n"
    }

    return result
}