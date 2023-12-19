import com.boomi.document.scripting.DataContext
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
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
     * Run script with empty DataContext => no documents!
     */
    @Test
    void test01() {
        DataContext dc = _testScript.run(DataContext.create())
        Helper.printTextDocuments(dc)
    }


    /**
     * Run script with two Strings as documents!
     */
    @Test
    void test02() {
        DataContext dc = _testScript.run(DataContext.create([
                Document.fromText("Document A"),
                Document.fromText("Document B"),
        ]))
        Helper.printTextDocuments(dc)
    }

    /**
     * Run script with two Strings as documents, each Document with a DDP_V1, DDP_V2 attached
     */
    @Test
    void test03() {
        DataContext dc = _testScript.run(DataContext.create([
                Document.fromText("Document A",
                        /* DDPs: */ [DDP_V1: "Doc1 V1", DDP_V2: "Doc1 V2"]),
                Document.fromText("Document B",
                        /* DDPs: */ [DDP_V1: "Doc2 V1", DDP_V2: "Doc2 V2"]),
        ]))
        Helper.printTextDocuments(dc)
    }
}
//* {@see https://help.boomi.com/bundle/integration/page/c-atm-Groovy_Accessing_process_properties.html}
