package MapScript

import com.boomi.execution.ExecutionUtil

/*
    The hello world script logs the current AtomID (taken from the execution context).
    Then it does some math.

    * INPUT Parameters (DataContext):
        - a, b
    * OUTPUT Parameters
        - total
 */

logger = ExecutionUtil.getBaseLogger()
ExecutionId = ExecutionUtil.getRuntimeExecutionProperty('EXECUTION_ID')
//ExecutionManager.getCurrent().getExecutionProperty('EXECUTION_ID');
ProcessId = ExecutionUtil.getRuntimeExecutionProperty('PROCESS_ID')
ProcessName = ExecutionUtil.getRuntimeExecutionProperty('PROCESS_NAME')

String atomId = ExecutionUtil.getRuntimeExecutionProperty("ATOM_ID")
logger.fine("atom id: " + atomId)

logger.fine( 'A=' + a )
logger.fine( 'B=' + b )

// OUTPUT parameters
total = a + b
logger.info("Total: " + total)