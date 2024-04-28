package processScript

import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ProcessScript
import msPro.mgf4boomi.ProcessScriptContext
import msPro.mgf4boomi.TestFilesHelper
import org.junit.Test

@TypeChecked
class Test_ProcessScriptDemo {

	@SourceURI
	URI _sourceUri
	final ProcessScript _testScript = new ProcessScript("psgDemo.groovy", _sourceUri)
	
	final TestFilesHelper _testFiles = new TestFilesHelper( "testData", _sourceUri)


	/**
	 * Test 
	 */
	@Test
	void test01() {

		ProcessScriptContext testContext = new ProcessScriptContext()
		// Initialize 
		// * Execution context          : executionContexts
		// * Dynamic Process Properties : dynProcPros
		// * Process Properties         : procPros
		// * Documents                  : inputDocuments
		//      incl. Dynamic Document Properties
		// --------------------------------------------------------------

		// testContext.executionProperties.ACCOUNT_ID = "IntelliJ_IDEA-M42S66"

		testContext.dynProcPros.DPP_ProcPropString = "My Process Property"
		testContext.dynProcPros.DPP_IntValue = 0

		// region Process Property 

		final String PROCESS_PROPERTY_COMPONENT_ID = "8fb41f63-a988-4778-8cc8-0144f30ace81"
		final String VAL1_ID = "eea9e988-cb14-4a84-ba37-ee455451a741"
		final String VAL2_ID = "2c68fb60-8431-46cc-9da9-cbe10d446a0e"

		// Wrap key in parenthesis so that the variables (Ids) are taken
		// and not the text as a string


		def procPropValue1 = 4711
		def procPropValue2 = "Markus Schmidt"
		
		testContext.procPros = [(PROCESS_PROPERTY_COMPONENT_ID): [
				(VAL1_ID): procPropValue1,
				(VAL2_ID): procPropValue2
		]]
		// endregion

		// region Documents

		final String DDP_Name = "DDP_IntValue"
		int ddpValue = 10
		testContext.inputDocuments = [
				Document.fromText("Document 01 content", [(DDP_Name): ddpValue]),
				// Document.fromFile( _testFiles.get( "demo.json") )
		]

		// endregion

		_testScript.run(testContext)

		//
		// Check and print test result for each document
		//
		println("Script Test Output")
		println("------------------")
		
		
		int documentCount = testContext.outputDocuments.size()
		println("${testContext.outputDocuments.size()} Document(s) after script execution")
		assert documentCount == testContext.outputDocuments.size() 

		assert testContext.dynProcPros.DPP_IntValue == 1

		def pp = testContext.procPros[PROCESS_PROPERTY_COMPONENT_ID]
		assert pp[ VAL1_ID] == procPropValue1 + 1
		assert pp[ VAL2_ID] == procPropValue2
		
		
		for (int docNo = 0; docNo < documentCount; docNo++) {
			Document doc = testContext.outputDocuments[docNo]
			String docText = doc.toString()
			assert docText != "", "Document is null"

			for (def p in doc.getProperties()) println("${p.key}='${p.value}'")

			int newDDPValue = doc.getProperty(DDP_Name) as int
			assert  newDDPValue ==  ddpValue+1
			println("${DDP_Name}=${newDDPValue}")
		}
	}
}
