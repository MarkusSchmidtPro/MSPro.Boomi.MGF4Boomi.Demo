package MapScript

import com.boomi.execution.ExecutionUtil

final String SCRIPT_NAME = "msgHelloWorld"


/** 
    The hello world script logs the current AtomID (taken from the execution context).
    Then it does some math.

    * INPUT Parameters (DataContext):
        - a, b
    * OUTPUT Parameters
        - total
 */

_logger = ExecutionUtil.getBaseLogger()
_logger.info('>>> Start Script ' + SCRIPT_NAME)

try {
    ExecutionId = ExecutionUtil.getRuntimeExecutionProperty('EXECUTION_ID')
//ExecutionManager.getCurrent().getExecutionProperty('EXECUTION_ID');
    ProcessId = ExecutionUtil.getRuntimeExecutionProperty('PROCESS_ID')
    ProcessName = ExecutionUtil.getRuntimeExecutionProperty('PROCESS_NAME')

    String atomId = ExecutionUtil.getRuntimeExecutionProperty("ATOM_ID")
    _logger.fine("atom id: " + atomId)

    _logger.fine('A=' + a)
    _logger.fine('B=' + b)

    // OUTPUT parameters
    total = a + b
    _logger.info("Total: " + total)

    _logger.info('<<< End Script')
}
catch (Exception e) {
    _logger.severe(e.message)
    throw e
}