
import com.boomi.document.scripting.DataContext
import groovy.json.JsonOutput
import groovy.transform.TypeChecked
import msPro.mgf4boomi.DataContextHelper
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

/**
 * TEST to demo MGF4Boomi functionality.
 */
@TypeChecked
class psgTraceTest {

    final ProcessScript _testScript = new ProcessScript('psgTrace.groovy')
    final String TESTDATA_DIR = 'TestData/JSONSamples/'


    /**
     * psgTrace with no documents.
     */
    @Test
    void test01() {
        // 
        // Run script with empty DataContext => no documents!
        //
        DataContext dc = _testScript.run( DataContext.create() )

        int docNo = 0;
        for (def doc in dc.Documents) {
            println('Doc[' + docNo++ + ']:' + JsonOutput.prettyPrint(doc.toString()))
        }

    } 
}
