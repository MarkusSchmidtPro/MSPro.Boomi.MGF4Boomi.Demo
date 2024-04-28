package templatesSource

import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ProcessScript
import msPro.mgf4boomi.ProcessScriptContext
import org.junit.Test

@TypeChecked
class Test_ProcessScript {

	@SourceURI
	URI _sourceUri
	final ProcessScript _testScript = new ProcessScript("${ProcessScriptName}.groovy", _sourceUri)


	/** A short description what this test is supposed to do. */
	@Test
	void test01() {

		ProcessScriptContext context = new ProcessScriptContext()

		// Initialize 
		// * Execution Properties       : executionProperties
		// * Dynamic Process Properties : dynProcPros
		// * Process Properties         : procPros
		// * Documents                  : inputDocuments
		//      incl. Dynamic Document Properties
		// --------------------------------------------------------------

		// context.executionProperties.ACCOUNT_ID = "IntelliJ_IDEA-M42S66"
		context.dynProcPros.DPP_ProcPropString = "My Process Property"

		// region Process Property 

		final String PROCESS_PROPERTY_COMPONENT_ID = "8fb41f63-a988-4778-8cc8-0144f30ace81"
		final String VAL1_ID = "eea9e988-cb14-4a84-ba37-ee455451a741"
		context.procPros = [(PROCESS_PROPERTY_COMPONENT_ID): [(VAL1_ID): 4711]]
		
        // endregion

		context.inputDocuments = [
				Document.fromText("Document 01 content", [
                        ( "DDP_IntValue"): 10
                ])
		]

		_testScript.run(context)

        println("\r\n--- Test Output ----------")

        int docCount = context.outputDocuments.size()
        println("${ docCount} Document(s) after script execution")
        assert context.inputDocuments.size() == docCount
       
        for (Document doc in context.outputDocuments) {
            String textDoc = doc.toString()
            assert textDoc != "", "Document is null"
            //println("Doc:" + textDoc)

            // Access Dynamic Document Properties 
            
            for (def p in doc.getProperties()) println("${p.key}='${p.value}'")

            int newDDPValue = doc.getProperty("DDP_IntValue") as int
            assert newDDPValue == 11
            println("DDP_IntValue=" + newDDPValue)
        }
        
        // Access Process properties
        def pp = context.procPros[PROCESS_PROPERTY_COMPONENT_ID]
        assert pp[VAL1_ID] == 4712

        assert context.dynProcPros.DPP_ProcPropString == "My Process Property"
    }
}
