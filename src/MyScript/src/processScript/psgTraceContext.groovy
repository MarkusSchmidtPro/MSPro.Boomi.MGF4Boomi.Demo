package ProcessScript

/// *************** COPY AND PASTE FROM HERE *****************

import com.boomi.execution.ExecutionUtil
import groovy.json.JsonOutput

final String SCRIPT_NAME = "psgTraceContext"


/**
 * A demo PROCESS script to trace current context information.
 *
 * {@see https://help.boomi.com/bundle/integration/page/c-atm-Groovy_Accessing_process_properties.html}

 * Parameters: 
 *  ...
 *
 * Returns: 
 *  ...
 *
 * ------------------------------------------------------------------------
 * 2022-06-26      msc -   Created
 */

_logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

try {
    final Object dataContextJson = JsonOutput.toJson(dataContext)
    _logger.info(JsonOutput.prettyPrint(dataContextJson))

    _logger.info('DOC Count=' + dataContext.getDataCount())
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        String docText = _getTextDocument(dataContext, docNo)
        Properties props = dataContext.getProperties(docNo)

        _logger.info('Doc[' + docNo + ']:"' + docText + '"')

        _setTextDocument(dataContext, docText, props)

        jsonDocument = JsonOutput.toJson(outJson)
        _setTextDocument(
            dataContext, jsonDocument, 
            dataContext.getProperties(docNo) as Properties)
        _logger.info('<<< End Script')
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

static String _getTextDocument(dataContext, int docNo) {
    InputStream documentStream = dataContext.getStream(docNo)
    return documentStream.getText("UTF-8")
}

static void _setTextDocument(dataContext, String value, Properties props) {
    InputStream newDocumentStream = new ByteArrayInputStream(value.getBytes("UTF-8"))
    dataContext.storeStream(newDocumentStream, props)
}