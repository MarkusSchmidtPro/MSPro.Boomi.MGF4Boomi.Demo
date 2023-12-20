package processScript

/// *************** COPY AND PASTE FROM HERE *****************

import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "psgProperties_PP"

/** Demo how to use Process Properties in Groovy
 *   
 * ------------------------------------------------------------------------
 * 2023-12-20   msc -   Created
 */

_logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

try {
    // Process Properties have two parts:
    // a) the Process Property Component ID
    // b) the ID of the value in that component
    
    // The IDs here example IDs. You must copy and paste the IDs from your
    // Boomi repository, to make that script work!
    
    final String COMPONENT_ID   = "8fb41f63-a988-4778-8cc8-0144f30ace81"
    final String VAL1_ID        = "eea9e988-cb14-4a84-ba37-ee455451a741"
    final String VAL2_ID        = "2c68fb60-8431-46cc-9da9-cbe10d446a0e"

    // Retrieve a Process Property component value
    def myValue = ExecutionUtil.getProcessProperty( COMPONENT_ID, VAL1_ID);
    _logger.info("pp.MyProcess.Val1=$myValue")
    
    // Set a Process Property component value
    ExecutionUtil.setProcessProperty(COMPONENT_ID, VAL1_ID, myValue+1);


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