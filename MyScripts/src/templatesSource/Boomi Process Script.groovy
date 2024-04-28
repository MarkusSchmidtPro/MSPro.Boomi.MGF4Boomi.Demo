package templatesSource

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "${FILE_NAME}"

/* **************************************************************************
    ${SingleLineDescription}
        
    IN: [Describe inbound arguments]
    IN: [Describe outbound arguments]
    ------------------------------------------------
    ${DATE}  ${Author} -   Created
	Template v0.2.0
************************************************************************** */
final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

try {
    int docCount = dataContext.getDataCount()
    _logger.fine("In-Document Count=${  docCount}")

    for (int docNo = 0; docNo < docCount; docNo++) {
        final String textDoc = _getTextDocument(dataContext, docNo)
        final Properties props = dataContext.getProperties( docNo)

        // *********** Script functionality ************
        
        // region DemoCode 
        // The functionality below is demo, only.
        // *** Replace with your code! ***
        
        //_logger.info( "Doc[$docNo]='$textDoc'")       // Log the document itself --> Boomi Trace

        // Increment Dynamic Document Property
        final String DDP_PROP_NAME = "DDP_IntValue"
        int ddpVal1 = _getDDP( props, DDP_PROP_NAME) as int
        _setDDP( props, DDP_PROP_NAME, ddpVal1 +1)
        _logger.info("${DDP_PROP_NAME}='${ddpVal1}'")

        // increment DDP_Value and DPP_Value 

        final String DPP_PROP_NAME = "DPP_IntValue"
        int dppValue = _getDPP( DPP_PROP_NAME ) as int
        _setDPP(DPP_PROP_NAME, dppValue +1 )
        _logger.info("${DPP_PROP_NAME}='${dppValue}'")

        final String PROCESS_PROPERTY_COMPONENT_ID = "8fb41f63-a988-4778-8cc8-0144f30ace81"
        final String VAL1_ID = "eea9e988-cb14-4a84-ba37-ee455451a741"
        int ppVal1 = ExecutionUtil.getProcessProperty(PROCESS_PROPERTY_COMPONENT_ID, VAL1_ID) as int
        ExecutionUtil.setProcessProperty(PROCESS_PROPERTY_COMPONENT_ID, VAL1_ID, ppVal1+1)
        // endregion
        
        // ******** end of Script functionality ********

        _setTextDocument(dataContext, textDoc, props)
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
static String _getDPP(String propertyName, Object defaultValue = null) {
    String v = ExecutionUtil.getDynamicProcessProperty(propertyName)
    if (v == null || v.isEmpty()) {
        // Default handler
        if (defaultValue == null) throw new Exception("Mandatory " + propertyName + " not set.")
        v = defaultValue
    }
    return v
}

/** Set (create or update) a Dynamic Process Property.
 *
 * @param propertyName
 * @param value
 */
static void _setDPP(String propertyName, Object value) {
    ExecutionUtil.setDynamicProcessProperty(propertyName, value, false)
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

static String _setDDP(Properties docProperties, String propertyName, Object value) {
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
    docProperties.setProperty( userDefinedPropertyBase + propertyName, value as String)
}
