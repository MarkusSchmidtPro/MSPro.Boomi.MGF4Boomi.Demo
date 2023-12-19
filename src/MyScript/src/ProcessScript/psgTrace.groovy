package ProcessScript

/// *************** COPY AND PASTE FROM HERE *****************

import com.boomi.execution.ExecutionUtil
import groovy.json.JsonOutput

final String SCRIPT_NAME = "psgTrace"

/**
 * A PROCESS script that pretty-prints (write) the current dataContext
 * and all documents and dynamic document properties to the trace 
 * (see Debug Windows / Boomi Reporting). 
 *
 * Parameters   : n/a
 * Returns      : Input documents pass through
 * ------------------------------------------------------------------------
 * 2023-12-19   msc -   Refactored
 * 2022-06-26   msc -   Created
 */

_logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

final String userDefinedPropertyBase = 'document.dynamic.userdefined.'


try {
    _logger.info("DOC Count=${dataContext.getDataCount()}")

    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        String docText = _getTextDocument(dataContext, docNo)
        Properties dynDocProperties = dataContext.getProperties(docNo)

        // *********** Script functionality ************
        // Log document content and all DDPs

        _logger.info("Doc[$docNo]=$docText")
        for (def ddp in dynDocProperties) _logger.info("-- ${ddp.key}=\"${ddp.value}\"")
       

        // ******** end of Script functionality ********
        _setTextDocument(dataContext, docText, dynDocProperties)
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