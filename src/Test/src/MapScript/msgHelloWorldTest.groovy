package MapScript

import groovy.transform.TypeChecked
import msPro.mgf4boomi.ExecutionUtilContexts
import msPro.mgf4boomi.MapScript
import org.junit.Test 



/**
 * A Test class that contains one or more test methods to test our msgHelloWorld script.
 * 
 * For more information on testing see: https://junit.org/junit4/
 */
@TypeChecked
class HelloWorldTest {
    
    // Specify the script's filename that you want to test.
    static final String ScriptFilename = "msgHelloWorld.groovy"
    

    /** Your first Map Script test.
     * 
     * 1. Create a Map script context:
     *    a) InputParameters
     *    b) Define an ExecutionUtilContext.mpty() in that case
     *    c) [optional] Document properties
     *    
     * 2. Run (and debug) the script 
     */
    @Test
    void test01() {
        //
        // A Map script requires an ExecutionContext and
        // a Map with an entry for each input parameter.
        // The output is another Map containing all output parameters.
        //
        def inputParameters = [
                a: 5,
                b: 7
        ]

        final MapScript script = new MapScript(ScriptFilename)
        def outputParameters = script.run( ExecutionUtilContexts.empty(), inputParameters)

        // Print to console windows and validate results
        println( "Test Total = " + outputParameters.total)
        assert  outputParameters.total == inputParameters.a + inputParameters.b
    }
}
