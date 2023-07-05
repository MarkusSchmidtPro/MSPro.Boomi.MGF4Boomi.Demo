package MapScript

import com.boomi.execution.ExecutionUtil

// Give the script a meaningful name
// Start with msg -> Message Script
final String SCRIPT_NAME = "msgHelloWorld"

/** ================================================================================== 
    The hello world script logs the current AtomID (taken from the execution context).
    Then it does some math.
 
    INPUT Parameters (DataContext):
        a       : Parameter 1
        b       : Parameter 2

    OUTPUT Parameters
        total   : sum( a, b)
 
    ----------------------------------------------------------------------------------
    2022-07-01  msc -   Created
    ==================================================================================
*/

// Get a logger instance that can be used to log messages 
// to the Boomi Platform (-> Process Reporting) 
_logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

try {
    /*
     * Get the runtime execution properties and save into local variables.
     */
    String executionId = ExecutionUtil.getRuntimeExecutionProperty('EXECUTION_ID')
    String processId = ExecutionUtil.getRuntimeExecutionProperty('PROCESS_ID')
    String processName = ExecutionUtil.getRuntimeExecutionProperty('PROCESS_NAME')
    String atomId = ExecutionUtil.getRuntimeExecutionProperty("ATOM_ID")
    
    // Write information to process reporting
    _logger.fine("atom id: " + atomId)
    _logger.fine('A=' + a)
    _logger.fine('B=' + b)

    // This is the script's logic - not much - but anyways...
    total = a + b
    
    // Log the result and the end of the execution to process reporting
    _logger.info("Total: " + total)
    _logger.info('<<< End Script')
}
catch (Exception e) {
    _logger.severe(e.message)
    throw e
}