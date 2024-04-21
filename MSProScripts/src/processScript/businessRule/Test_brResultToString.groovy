package processScript.businessRule

import com.boomi.document.scripting.DataContext
import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

@TypeChecked
class Test_brResultToString {

    @SourceURI
    URI _sourceUri
    final ProcessScript _testScript = new ProcessScript("/brResultToString.groovy", _sourceUri)

    /**
     * Test 
     */
    @Test
    void test01() {
        ExecutionContexts ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_ProcProp = "My Process Property"
        // ToDo: toTemplate
        List<Document> documents = [
                Document.fromText(
                        '''empty01''',
                        [DDP_business_rule_result: '''<business_rule_failures>\n
                                                   <business_rule_failure rule=\"Validate PLZ\">null ist nicht befüllt</business_rule_failure>\n
                                                </business_rule_failures>''']),
                Document.fromText(
                        '''empty02''',
                        [DDP_business_rule_result: '''<business_rule_failures>\n
                                                   <business_rule_failure rule=\"Validate PLZ\">PLZ ist nicht befüllt</business_rule_failure>\n
                                                   <business_rule_failure rule=\"Validate Ort\">Ort ist nicht befüllt</business_rule_failure>\n
                                                </business_rule_failures>''']),
        ]
        def dc = _testScript.run(DataContext.create(documents), ec)

        //
        // Check and print test result for each document
        //
        println("${dc.dataCount} Documents after script execution")
        // ToDo: toTemplate
        assert dc.dataCount == documents.size()

        for (int docNo = 0; docNo < dc.dataCount; docNo++) {
            Document doc = dc.Documents[docNo]
            String docText = doc.toString()
            assert docText != "", "Document is null"

            //for (def p in doc._dynamicDocumentProperties) println("${p.key}='${p.value}'")

            String DDP_business_rule_result = doc._dynamicDocumentProperties.getProperty( "document.dynamic.userdefined.DDP_business_rule_result") as String
            println(DDP_business_rule_result)
        }
    }
}
