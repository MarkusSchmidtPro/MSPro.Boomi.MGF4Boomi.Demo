package ProcessScript

import com.boomi.document.scripting.DataContext
import groovy.json.JsonOutput
import groovy.transform.TypeChecked
import msPro.mgf4boomi.DataContextHelper
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionUtilContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

/**
 * TEST serialization of a DataContext object.
 */
@TypeChecked
class psgJsonArrayToDocsTest {

    final ProcessScript _testScript = new ProcessScript('psgJsonArrayToDocs.groovy')
    final String TESTDATA_DIR = 'TestData/JSONSamples/'


    /**
     * Test split single document with some Array items, using "(root)".
     */
    @Test
    void test01() {
        //
        // Create and init [DPP_JsonArrayName]
        //
        def ec = new ExecutionUtilContexts()
        ec.initAddDynamicProcessProperty('DPP_JsonArrayName', '(root)')

        //
        // DataContext with a single document
        //
        DataContext inDataContext = DataContext.withDocuments(
                [
                        Document.fromFile(TESTDATA_DIR + "sampleArray.json")
                ]
        )

        DataContextHelper dch = _testScript.run(ec, inDataContext)
        
        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        assert dch.outDocumentCount==4
        
        for (int docNo = 0; docNo < dch.outDocumentCount; docNo++) {
            //Properties docProperties = dataContext.getProperties(docNo);
            String docText = dch.getOutDocumentText(docNo)
            println('Doc[' + docNo + ']:' + JsonOutput.prettyPrint(docText))
        }
    } 

    /**
     * Test split single document with some Array items, with no DPP set - default.
     */
    @Test
    void test02() {
        // Use empty execution context with no DPP
        def ec = ExecutionUtilContexts.empty()

        //
        // DataContext with a single document
        //
        DataContext inDataContext = DataContext.withDocuments(
                [
                        Document.fromFile(TESTDATA_DIR + "sampleArray.json")
                ]
        )

        DataContextHelper dch = _testScript.run(ec, inDataContext)
        
        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        assert dch.outDocumentCount==4
        
        for (int docNo = 0; docNo < dch.outDocumentCount; docNo++) {
            //Properties docProperties = dataContext.getProperties(docNo);
            String docText = dch.getOutDocumentText(docNo)
            println('Doc[' + docNo + ']:' + JsonOutput.prettyPrint(docText))
        }
    }



    @Test
    void testCustom() {
        // Use empty execution context with no DPP
        def ec = ExecutionUtilContexts.empty()

        //
        // DataContext with a single document
        //
        DataContext inDataContext = DataContext.withDocuments(
                [
                        Document.fromFile(TESTDATA_DIR + "customerSample.json")
                ]
        )

        DataContextHelper dch = _testScript.run(ec, inDataContext)

        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        assert dch.outDocumentCount==2

        for (int docNo = 0; docNo < dch.outDocumentCount; docNo++) {
            //Properties docProperties = dataContext.getProperties(docNo);
            String docText = dch.getOutDocumentText(docNo)
            println('Doc[' + docNo + ']:' + JsonOutput.prettyPrint(docText))
        }
    }
}
