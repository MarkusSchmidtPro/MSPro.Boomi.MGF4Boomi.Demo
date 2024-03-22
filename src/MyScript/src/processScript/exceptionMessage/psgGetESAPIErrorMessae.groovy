package processScript.exceptionMessage

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

final String SCRIPT_NAME = "psgGetESAPIErrorMessage"

/* **************************************************************************
    Extract an ESAPI error message from an ESAPI Json response into 
    (profile [FLx] ESAPI error message) into 
    DDP_ESAPIErrorMessage dynamic document property-
    {
        "trace" : SOAPResponse
    }
            
    --------------------------------------------
    2024-03-19  msc -   Created
************************************************************************** */

final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

_logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

final JsonSlurper slurper = new JsonSlurper()
try {
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        Properties docProperties = dataContext.getProperties(docNo) as Properties
        String document = _getTextDocument(dataContext, docNo)
        def jsonDocument = slurper.parseText(document)
        // ------------------------------------------------------------------------

        String errorMessage = StringUtils.substringBetween( jsonDocument.trace, "SOAPFaultException:", "\n\tat")
        docProperties.setProperty( userDefinedPropertyBase + "DDP_ESAPIErrorMessage", errorMessage)
        
        // ------------------------------------------------------------------------
        dataContext.storeStream(documentStream, docProperties)
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
