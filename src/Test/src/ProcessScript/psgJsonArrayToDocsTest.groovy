package ProcessScript

import com.boomi.document.scripting.DataContext
import groovy.json.JsonOutput
import groovy.transform.TypeChecked
import msPro.mgf4boomi.DataContextHelper
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
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
        def ec = new ExecutionContexts()
        ec.initAddDynamicProcessProperty('DPP_JsonArrayName', '(root)')

        //
        // DataContext with a single document
        //
        DataContext dc = DataContext.create(
                [
                        Document.fromFile(TESTDATA_DIR + "sampleArray.json")
                ]
        )

        _testScript.run(dc, ec)

        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        assert dc.Documents.size() == 4

        for (int docNo = 0; docNo < dc.Documents.size(); docNo++) {
            //Properties docProperties = dataContext.getProperties(docNo);
            String docText = dc.Documents[docNo]
            println('Doc[' + docNo + ']:' + JsonOutput.prettyPrint(docText))
        }
    }

    /**
     * Test split single document with some Array items, with no DPP set - default.
     */
    @Test
    void test02() {
        //
        // DataContext with a single document
        //
        DataContext dc = DataContext.create(
                [
                        Document.fromFile(TESTDATA_DIR + "sampleArray.json")
                ]
        )

        _testScript.run(dc)

        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        assert dc.Documents.size() == 4

        int docNo = 0;
        for (def doc in dc.Documents) {
            println('Doc[' + docNo++ + ']:' + JsonOutput.prettyPrint(doc.toString()))
        }
    }


    @Test
    void testCustom() {
        //
        // DataContext with a single document
        //
        DataContext dc = _testScript.run(
                DataContext.create(
                        [
                                Document.fromFile(TESTDATA_DIR + "customerSample.json")
                        ]
                ))

        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        assert dc.Documents == 2

        int docNo = 0;
        for (def doc in dc.Documents) {
            println('Doc[' + docNo++ + ']:' + JsonOutput.prettyPrint(doc.toString()))
        }
    }
}
