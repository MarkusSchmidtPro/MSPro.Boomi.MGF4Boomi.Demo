
import groovy.transform.TypeChecked
import msPro.mgf4boomi.MapScript
import org.junit.Test


/**
 * A Test class that contains one or more test methods to test our msgHelloWorld script.
 *
 * For more information on testing see: https://junit.org/junit4/
 */
@TypeChecked
class HelloWorld_Test {

    // Specify the Boomi Script that your want to test in this class.
    final MapScript _testScript = new MapScript("msgHelloWorld.groovy")

    /** Your first Map Script Test. */
    @Test
    void test01() {
        //
        // A Map Script Test provides the mapping input parameters in the scriptContext,
        // as they are defined in Boomi.
        // Script Output variables are also added to that context
        // and can be validated after the execution.
        //
        HashMap scriptContext = [
                a: 5,
                b: 7
        ]
        _testScript.run(scriptContext)

        // -----------------------------------------------------
        // Perform your tests here to check whether the script 
        // execution met your expectation.
        assert scriptContext.total != null, "Script did not set 'total' as output parameter!"
        assert scriptContext.total == scriptContext.a + scriptContext.b, "Calculation result does not meet expectations!"

        // Print to console windows and validate results
        println("Test Total = " + scriptContext.total)
    }
}
