package ProcessScript

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

final String SCRIPT_NAME = "${ScriptName}"

/* **************************************************************************
    ${SingleLineDescription}
    ------------------------------------------------
    ${DATE}  ${Author} -   Created
************************************************************************** */

final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

final JsonSlurper js = new JsonSlurper()
final XmlSlurper  xs = new XmlSlurper()

try {
    _logger.fine('In-Document Count= ' + dataContext.getDataCount())
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        String document = _getTextDocument(dataContext, docNo)
        Properties props = dataContext.getProperties(docNo)

        // XML or JSON input document
        def xmlDoc = xs.parseText(document) 
        
        // *********** Script functionality ************

        // your code here
        _logger.info( "My Code")           
                
        
        
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
