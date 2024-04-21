package processScript.utils

import com.boomi.document.scripting.DataContext
import groovy.json.JsonOutput
import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

@TypeChecked
class Test_TraceContext {

    @SourceURI
    URI _sourceUri
    final ProcessScript _processScript = new ProcessScript("psgTraceContext.groovy", _sourceUri)

    /**
     * Test 
     */
    @Test
    void test01() {

        ExecutionContexts ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_ProcProp = "My Process Property"

        // Replace with your set-up
        def dc = _processScript.run(
                DataContext.create(
                        [
                                Document.fromText('''DOC01 Empty''', [DDP_NewName: "Lieschen Mueller"]),
                        ]
                ), ec)

        //
        // Check and print test result for each document
        //
        println("${dc.dataCount} Documents after script execution")
        assert dc.dataCount == 1

        //final JsonSlurper js = new JsonSlurper()

        for (int docNo = 0; docNo < dc.dataCount; docNo++) {
            Document doc = dc.Documents[docNo]
            String docText = doc.toString()
            assert docText != "", "Document is null"
            //Map jDoc = js.parseText(docText) as Map

            // Print document and document properties
            println("Doc[" + docNo + "]: " + JsonOutput.prettyPrint(doc.toString()))
            for (def p in doc._dynamicDocumentProperties) {
                println("${p.key}='${p.value}'")
            }
        }
    }
}
