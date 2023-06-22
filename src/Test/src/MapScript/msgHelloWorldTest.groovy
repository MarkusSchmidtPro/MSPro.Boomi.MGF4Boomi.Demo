package MapScript

import groovy.transform.TypeChecked
import msPro.mgf4boomi.ExecutionUtilContexts
import msPro.mgf4boomi.MapScript
import org.junit.Test 



/**
 * TEST serialization of a DataContext object.
 */
@TypeChecked
class HelloWorldTest {
    
    // The filename in the 01-MyScript project MapScript folder
    // that contains the Groovy Script to be tested.
    static final String ScriptFilename = "msgHelloWorld.groovy"
    
    final MapScript _script = new MapScript(ScriptFilename)


    /** Your first Map Script test.
     * 
     * Review the script in ScriptFilename 
     * to see what the Boomi Groovy Script is supposed to do.
     */
    @Test
    void test01() {

        //
        // A Map script requires an ExecutionContext and
        // a Map with an entry for each input parameter.
        // The output is another Map containing all output parameters.
        //
        Map inputParameters = [
                a: 5,
                b: 7
        ]
        
        Map outputParameters = _script.run( ExecutionUtilContexts.empty(), inputParameters)

        println( "Test Total = " + outputParameters.total)
        assert  outputParameters.total == inputParameters.a + inputParameters.b
    }
}
