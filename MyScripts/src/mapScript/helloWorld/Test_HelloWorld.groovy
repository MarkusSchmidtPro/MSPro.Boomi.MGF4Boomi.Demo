package mapScript.helloWorld

import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.MapScript
import org.junit.Test


/**
 * A Test class that contains one or more test methods to test our msgHelloWorld script.
 *
 * For more information on testing see: https://junit.org/junit4/
 */
@TypeChecked
class Test_HelloWorld {

	@SourceURI
	URI _sourceUri

	// Specify the Boomi Script that your want to test in this class.
	final MapScript _testScript = new MapScript("msgHelloWorld.groovy", _sourceUri)

	/** Your first Map Script Test. */
	@Test
	void test01() {
		//
		// A Map Script Test provides the mapping input parameters in the scriptContext,
		// as they are defined in Boomi.
		// Script Output variables can be validated after the execution.
		//
		def variables = _testScript.run([a: 5, b: 7])

		println("\r\n--- Test Output ----------")
		assert variables.total != null, "Script did not set 'total' as output parameter!"
		assert variables.total == (variables.a as int) + (variables.b as int), "Calculation result does not meet expectations!"

		// Print to console windows and validate results
		println("Test Total = " + variables.total)
	}
}
