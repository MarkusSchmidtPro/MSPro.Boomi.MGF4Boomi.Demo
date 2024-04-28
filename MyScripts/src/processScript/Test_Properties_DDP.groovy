package processScript


import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ProcessScript
import msPro.mgf4boomi.ProcessScriptContext
import org.junit.Test 
/**
 * Demo Dynamic Document Properties
 */
@TypeChecked
class Test_Properties_DDP {

	@SourceURI
	URI _sourceUri


	/**
	 * Run script with two Strings as documents, 
	 *  each Document with two Dynamic Document Properties:
	 *      DDP_V1 and DDP_V2 
	 *  attached. 
	 */
	@Test
	void test01() {
		ProcessScriptContext context = new ProcessScriptContext(
				inputDocuments: [
						Document.fromText(
								"Document A",
								[
										// Provide Dynamic Document Properties 
										// as a key/value map with each document
										DDP_V1: "Doc1 V1",
										DDP_V2: "Doc1 V2"
								]),
						Document.fromText(
								"Document B",
								[
										DDP_V1: "Doc2 V1",
										DDP_V2: "Doc2 V2"
								]),
				])

		final ProcessScript _testScript = new ProcessScript("psgTrace.groovy", _sourceUri)
		_testScript.run(context)
	}
}
