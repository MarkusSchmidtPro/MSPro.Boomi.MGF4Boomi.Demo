package processScript.propertiesReplace

import com.boomi.execution.ExecutionManager
import com.boomi.execution.ExecutionTask
import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "propertiesReplace"

/* **************************************************************************
    Replace dynamic document and process properties in all documents.
    
    Each incoming document is considered to be a template with placeholders in 
    curley brackets and a starting D: or P:  
        {D:dynamicDocumentPropertyName} | {P:dynamicProcessPropertyName}.
    The script finds and replaces all placeholders with their corresponding
    Dynamic Document or Process Property value.  
    
    The Script recognizes the following well-known tags:
    
	    {P:UtcNow}                  : 2024-06-26T16:16:36.085Z
	    {P:TraceId}                 : 6b7d-588c
	    {P:CurrentProcessName}
	    {P:CurrentProcessId}
	    {P:CurrentExecutionId}
	    {P:TopLevelProcessName}
	    {P:TopLevelProcessId}
	    {P:TopLevelExecutionId}
	    {P:ExecutionChain}          : All processes from in a list
	                                  Custom Main Process -> sub process 01 -> sub process 01.01
        
    IN :    A document used as a template.
            Dynamic Document Properties
            Dynamics Process Properties
            
    OUT:    The rendered document with replaced tags.
    
    ------------------------------------------------
    26.06.2024  mspro -   Created
    Template v0.2.1
************************************************************************** */


final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

try {
	int docCount = dataContext.getDataCount()
	_logger.fine("In-Document Count=" + docCount)

	for (int docNo = 0; docNo < docCount; docNo++) {
		String textDoc = _getTextDocument(dataContext, docNo)
		final Properties props = dataContext.getProperties(docNo)

		// *********** Document related functionality ************
		def resolver = new PropertyResolver(props)

		List<String> pTags = textDoc.findAll("\\{P\\:(.*?)\\}")
		for (String tag in pTags) {
			String propertyName = tag.substring(3, tag.length() - 1)
			textDoc = textDoc.replace(tag, resolver.getDPP(propertyName))
		}

		List<String> dTags = textDoc.findAll("\\{D\\:(.*?)\\}")
		for (String tag in dTags) {
			String propertyName = tag.substring(3, tag.length() - 1)
			textDoc = textDoc.replace(tag, resolver.getDDP( propertyName))
		}

		// ******** end of Document related functionality ********

		_setTextDocument(dataContext, textDoc, props)
	}

	// Your process related code (process properties etc.) here
	// ..
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

class PropertyResolver {
	final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
	final Properties _docProperties

	PropertyResolver(Properties docProperties) { _docProperties = docProperties }


	/** Get a dynamic process property.
	 *
	 * @param propertyName
	 * @param default If default is null, an exception is thrown when the property is not set.
	 *                      Otherwise, the default is returned in case it does not exist (is empty).
	 *                      Whitespaces are valid characters and a whitespaces string is not empty!
	 * @return The property value (or default)
	 */
	String getDPP(String propertyName, String defaultValue = null) {
		String v = ExecutionUtil.getDynamicProcessProperty(propertyName)
		if (v == null || v.isEmpty()) {
			v = _tryGetWellKnownProperty(propertyName)
			if (v == null) {
				if (defaultValue == null) throw new Exception("Mandatory $propertyName not set.")
				v = defaultValue
			}
		}
		return v
	}

	String getDDP(String propertyName, String defaultValue = null) {

		String v = _docProperties.getProperty(userDefinedPropertyBase + propertyName)
		if (v == null || v.isEmpty()) {
			// Default handler
			if (defaultValue == null) throw new Exception("Mandatory $propertyName not set.")
			v = defaultValue
		}
		return v
	}

	// ACCOUNT_ID,ATOM_ID,ATOM_NAME,DOCUMENT_COUNT,EXECUTION_ID,NODE_ID,PROCESS_ID,PROCESS_NAME"

	final LinkedHashMap<String, String> _wellKnownProperties = [:]

	private String _tryGetWellKnownProperty(String propertyName) {

		if (_wellKnownProperties.containsKey(propertyName)) return _wellKnownProperties[propertyName]

		//
		// lazy resolution
		//
		String value = null

		switch (propertyName) {
			case "CurrentProcessName": value = ExecutionUtil.getRuntimeExecutionProperty("PROCESS_NAME")
				break
			case "CurrentProcessId": value = ExecutionUtil.getRuntimeExecutionProperty("PROCESS_ID")
				break
			case "CurrentExecutionId": value = ExecutionUtil.getRuntimeExecutionProperty("EXECUTION_ID")
				break
			case "TopLevelProcessName": value = _getTopLevelExecutionTask(ExecutionManager.getCurrent()).processName
				break
			case "TopLevelProcessId": value = _getTopLevelExecutionTask(ExecutionManager.getCurrent()).processId
				break
			case "TopLevelExecutionId": value = _getTopLevelExecutionTask(ExecutionManager.getCurrent()).executionId
				break
			case "ExecutionChain":
				ExecutionTask t = ExecutionManager.getCurrent()
				value = t.processName
				while (t.getParent() != null) {
					t = t.getParent()
					value = t.processName + " -> " + value
				}
				break
			case "TraceId":
				value = _getTraceId()
				break
			case "UtcNow":
				value = new Date().format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", TimeZone.getTimeZone('UTC'))
				break
		}
		_wellKnownProperties[propertyName] = value
		return _wellKnownProperties[propertyName]
	}


	private static String _getTraceId() {
		long r = 0xFFFFFFFF * Math.random() as long
		return sprintf('%04x-%04x', (r & 0xFFFF0000) >> 16, r & 0x0000FFFF)
	}


	private static ExecutionTask _getTopLevelExecutionTask(ExecutionTask t) {
		while (t.getParent() != null) t = t.getParent()
		return t
	}
}