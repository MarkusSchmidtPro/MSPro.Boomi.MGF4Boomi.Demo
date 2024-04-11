
package ProcessScript

import com.boomi.document.scripting.DataContext
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

@TypeChecked
class ${SCRIPT_NAME}_Tests {

    final String SCRIPT_REL_DIR = '${SCRIPT_REL_DIR}'
    
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'
    final ProcessScript _testScript = new ProcessScript("/${SCRIPT_NAME}.groovy",SCRIPT_REL_DIR)

    /**
     * Test 
     */
    @Test
    void test01() {
        //
        // Define Dynamic Process Properties for testing
        //
        ExecutionContexts ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_ProcProp = "My Process Property"

        //
        // Create test documents and its dynamic document properties
        //
        
        // Replace with your set-up
        def dc = _testScript.run(
                DataContext.create(
                        [
                                // Create a JSON document in-line from text, 
                                // Set Dynamic Document Property DDP_V
                                Document.fromText('''
                                { 
                                    "contact_name" : "Markus Schmidt"
                                }
                                ''',  [ DDP_NewName: "Lieschen Mueller"] ),

                                // Load document from a file
                                // Document.fromFile(SCRIPT_REL_DIR + "${TestDataFileName}"),
                        ]
                ), ec)

        //
        // Check and print test result for each document
        //
        println("${dc.dataCount} Documents after script execution")
        assert dc.dataCount==1

        final JsonSlurper js = new JsonSlurper()

        for (int docNo=0; docNo<dc.dataCount; docNo++) {
            Document doc = dc.Documents[ docNo]
            String docText = doc.toString()
            assert docText != "", "Document is null"
            Map jDoc = js.parseText(docText) as Map
            
            // Print document and document properties
            println("Doc[" + docNo + "]: " + JsonOutput.prettyPrint(doc.toString()))
            for( def p in doc._dynamicDocumentProperties) { println( "${p.key}='${p.value}'") }

            assert jDoc.contact_Name == doc._dynamicDocumentProperties.DDP_NewName
        }
    }
}
