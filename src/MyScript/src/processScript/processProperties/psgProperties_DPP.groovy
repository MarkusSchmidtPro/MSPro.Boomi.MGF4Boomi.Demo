package processScript.processProperties
/// *************** COPY AND PASTE FROM HERE *****************

import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "psgProperties_DPP"

/**
 * Sample script how to work with dynamic process properties.
 * 
 * Input:   : DPP_DateTime
 * 
 * Returns  : DPP_Out (Boolean)
 *
 * * ------------------------------------------------------------------------
 * 2023-12-19   msc -   Refactored
 * 2022-06-26   msc -   Created
 */

def _logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

try {
    _logger.info("DOC Count=${dataContext.getDataCount()}")

    // Retrieve Dynamic Process Property value
    final String propName = "DPP_DateTime"
    def propValue = ExecutionUtil.getDynamicProcessProperty(propName)
    _logger.info("$propName = '$propValue'")

    // Access a not defined Property and write a WARNING
    final String propName2 = "unknown"
    def propUnknown = ExecutionUtil.getDynamicProcessProperty(propName2)
    if( propUnknown == null) _logger.warning("Unknown Dynamic Process Property: $propName2")

    // You can also set a DynamicProcessProperty
    // And check (assert) in your Test routine if it is set correctly.
    //   assert ec.dynamicProcessProperties.DPP_out == true
    // Here we set DPP_Out = true
    final String propName3 = "DPP_Out"
    ExecutionUtil.setDynamicProcessProperty(propName3, true, false)

    // ------------------------------------------------------
    // MANDATORY!!!
    // No document functionality: pass through all documents
    // ------------------------------------------------------
    documentsPassThrough()
    
    _logger.info('<<< End Script')
}
catch (Exception e) {
    _logger.severe(e.message)
    throw e
}

// =================================================
// -------------------- LOCALS ---------------------
// =================================================

/** Pass through all documents. */
private void documentsPassThrough() {
   
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        InputStream documentStream = dataContext.getStream(docNo)
        Properties dynDocProperties = dataContext.getProperties(docNo)
        // *********** Script functionality ************
        // ******** end of Script functionality ********
        dataContext.storeStream(documentStream, dynDocProperties)
    }
}