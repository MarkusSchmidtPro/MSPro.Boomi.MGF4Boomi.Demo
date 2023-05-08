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
class psgUC2_Test {

    //
    // Specify Test Script.
    //
    final ProcessScript _testScript = new ProcessScript('psgUC2.groovy')
    final String TESTDATA_DIR = 'TestData/XML/'

    @Test
    void test01() {
        //def ec = new ExecutionUtilContexts()
        //ec.initAddDynamicProcessProperty('DPP_JsonArrayName', '(root)')

        //
        // DataContext with a single document
        //
        DataContext inDataContext = DataContext.withDocuments(
                [
                        Document.fromFile(TESTDATA_DIR + "test01_in.xml")
                ]
        )

        DataContextHelper dch = _testScript.run(ExecutionUtilContexts.empty(), inDataContext)

        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        assert dch.outDocumentCount == 1

        for (int docNo = 0; docNo < dch.outDocumentCount; docNo++) {
            //Properties docProperties = dataContext.getProperties(docNo);
            String docText = dch.getOutDocumentText(docNo)
            println('Doc[' + docNo + ']:' + docText)
        }
    }
}
