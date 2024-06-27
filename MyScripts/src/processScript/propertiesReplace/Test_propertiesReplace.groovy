package processScript.propertiesReplace

import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ProcessExecutionProperties
import msPro.mgf4boomi.ProcessScript
import msPro.mgf4boomi.ProcessScriptContext
import org.junit.Test

@TypeChecked
class Test_propertiesReplace {
	final String SCRIPT_NAME = "propertiesReplace"

	@SourceURI
	URI _sourceUri
	final ProcessScript _testScript = new ProcessScript("psg" + SCRIPT_NAME + ".groovy", _sourceUri)


	/** A short description what this test is supposed to do. */
	@Test
	void test01() {
		// Initialize the Script Execution Context:
		// * Execution Properties       : executionProperties
		// * Dynamic Process Properties : dynProcPros
		// * Process Properties         : procPros
		// * Documents                  : inputDocuments
		//      incl. Dynamic Document Properties
		// --------------------------------------------------------------

		ProcessScriptContext context = new ProcessScriptContext(
				dynProcPros: [
						"DPP_01": "Dyn Proc Prop 01 Value"
				] as HashMap,
				inputDocuments: [
						Document.fromText('''
DDP_01: {D:DDP_01}
DPP_01: {P:DPP_01}
---
{P:UtcNow}
{P:TraceId}
{P:CurrentProcessName}
{P:CurrentProcessId}
{P:CurrentExecutionId}

{P:TopLevelProcessName}
{P:TopLevelProcessId}get
{P:TopLevelExecutionId}

{P:ExecutionChain}
{P:TraceId}
                ''',
					[
							DDP_01: "Dyn Document Prop 01 Value"
					] as HashMap)
				], processCallChain:
				[
						new ProcessExecutionProperties("Custom Main Process"),
						new ProcessExecutionProperties("sub process 01"),
						new ProcessExecutionProperties("sub process 01.01")
				])
		_testScript.run(context)

		println("\r\n--- Test Output ----------")

		int docCount = context.outputDocuments.size()
		println(docCount + " Document(s) after script execution")
		assert context.inputDocuments.size() == docCount

		for (Document doc in context.outputDocuments) {
			String textDoc = doc.toString()
			assert textDoc != "", "Document is null"
			println("Doc:" + textDoc)
		}
	}
}
