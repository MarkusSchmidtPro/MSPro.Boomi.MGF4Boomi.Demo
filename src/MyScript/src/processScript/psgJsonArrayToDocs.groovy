package processScript
/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

final String SCRIPT_NAME = "psgJsonArrayToDocs"


/* **************************************************************************
    A process script that iterates over all documents of any JSON profile type.
    
    For each document,
        the script searches for the JSON-Array @DPP_JsonArrayName and 
        creates a new output document for each object in that array.
        
        The number of output documents is number of 
        number input documents * number of array elements in each document.

    IN:     DPP_JsonArrayName (PROCESS PROPERTY)
                The name of the JSON object (of type Array).
                If [DPP_JsonArrayName] is not set (empty) it defaults to "(root)".

    OUT:    JSON documents
    --------------------------------------------
    2023-04-12  msc -   Support for [DPP_JsonArrayName] not set
    2022-07-07  msc -   Support for source section (jsonArray) is null
    2022-07-05  msc -   Created
************************************************************************** */

final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

final String DPPNAME_JSON_ARRAY_NAME = "DPP_JsonArrayName"
final JsonSlurper slurper = new JsonSlurper()

try {
    String jsonArrayElementName = _getDPP(DPPNAME_JSON_ARRAY_NAME)
    if (jsonArrayElementName == null) jsonArrayElementName = "(root)"

    String[] _sections = jsonArrayElementName != "(root)"
            ? jsonArrayElementName.split('\\.')
            : []

    /* 
        JSON Objects require >>def so that they can be Arrays or Objects
     */
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        String document = _getTextDocument(dataContext, docNo)
        def jsonDocument = slurper.parseText(document)

        def jsonObject = jsonGetSection(jsonDocument, _sections)
        if (jsonObject != null) 
        {
            if (!(jsonObject instanceof ArrayList))
                throw new Exception("Section $jsonArrayElementName is not of type ArrayList!")

            ArrayList jsonArray = (ArrayList) jsonObject
            // Create a new document from each Array element and 
            // write it to the output stream.
            // Because the number of input documents does not match 
            // the number of output documents, we cannot keep the input 
            // dynamic document properties and we must create a new, empty set of DDP 
            // for each output document: new Properties()
            for (int i = 0; i < jsonArray.size(); i++) 
            {
                _setTextDocument(dataContext,
                        JsonOutput.toJson(jsonArray[i]),
                        new Properties())
            }
        }
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

/**
 Get a Dynamic Process Property.

 @param propName
  The name of the Dynamic Process Property.
 @return
  The property value or [null] in case the property does not exist or 
  its value is blank (is not initialized).
 */
static String _getDPP(String propName) {
    String propValue = ExecutionUtil.getDynamicProcessProperty(propName)
    return (propValue == null || propValue.length() == 0) ? null : propValue
}

static def jsonGetSection(def root, String[] sections) {
    def current = root
    for (int i = 0; i < sections.size(); i++) {
        if (null == current[sections[i]]) return null
        current = current[sections[i]]
    }
    return current
}
