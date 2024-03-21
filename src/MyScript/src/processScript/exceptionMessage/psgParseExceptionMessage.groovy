package ProcessScript.exceptionMessage

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

final String SCRIPT_NAME = "psgParseExceptionMessage"

/* **************************************************************************
    The script parses an exception message and sets well-defined document properties.
    
    The [DDP_TryCatchMessage] must start with "Exception:" (case-sensitive) so that the script runs.
    The script parses the following text into dynamic document properties as follows:

        Exception: {DDP_ExceptionType}
        {DDP_ExceptionJson}
    
    The [DDP_ExceptionJson] is then interpreted as JSON and all elements on the first level 
    will be turned into dynamic document properties, too. All nested elements are skipped when creating DDPs.
    For example:
        DDP_TryCatchMessage=
            Exception: ApplicationException
            {
                "statusCode" : 404,
                "message" : "Record not found!"
            } 
        
        DDP_exceptionType='ApplicationException'
        DDP_exceptionJson='{
            "statusCode": 404,
            "message": "Record not found!"
        }'
        DDP_statusCode='404'
        DDP_message='Record not found!'


    IN:     DDP_TryCatchMessage 
            = Document Properties - Base - Try/Catch Message
        
    OUT:    DDP_ExceptionType - the text after "Exception:" (blanks truncated)
            DDP_ExceptionJson - The rest beginning after the first CRLF (second line)
        
    --------------------------------------------
    2024-03-19  msc -   Created
************************************************************************** */

final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

_logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

try {
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        InputStream documentStream = dataContext.getStream(docNo)
        Properties docProperties = dataContext.getProperties(docNo) as Properties
        // ------------------------------------------------------------------------

        String tryCatchMessage = docProperties[userDefinedPropertyBase + "DDP_TryCatchMessage"]
        if( tryCatchMessage != null && !tryCatchMessage.isBlank())
        {
            _logger.warning( tryCatchMessage)
            
            /*  Log document properties
                for( def p in docProperties) { _logger.fine( "${p.key}='${p.value}'") }
            */
            Map<String, String> props = parse( tryCatchMessage)
            for( def p in props){
                docProperties.setProperty( userDefinedPropertyBase + p.key, p.value)
            }
        }
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


Map<String,String> parse( String tryCatchMessage) {
    Map<String,String> result = [:]
    
    final String TOKEN = "Exception:"
    tryCatchMessage = tryCatchMessage.trim()
    if( !tryCatchMessage.startsWith("Exception:")) return result
    tryCatchMessage = tryCatchMessage.substring(TOKEN.size())
    int n =  tryCatchMessage.indexOf( '\n')
    if( n < 0) return result
    
    result.DDP_exceptionType =  tryCatchMessage.substring(0, n).trim()
    String json  = tryCatchMessage.substring(n).trim()
    
    try{
        final JsonSlurper slurper = new JsonSlurper()
        Map j = (Map) slurper.parseText( json)
        for( def element in j){
            result["DDP_${element.key}" as String] = element.value as String
        }

        json = JsonOutput.prettyPrint( json)
    }
    catch (Exception e) {
        json = e.message
    }

    result.DDP_exceptionJson = json
    for( def p in result) { _logger.fine( "${p.key}='${p.value}'") }
    return result
}