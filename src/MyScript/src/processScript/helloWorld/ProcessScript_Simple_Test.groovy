package processScript.helloWorld

import com.boomi.document.scripting.DataContext
import msPro.mgf4boomi.tests.TestHelper
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

/**
 * Demo MGF4Boomi functionality.
 */
@TypeChecked
class ProcessScript_Simple_Test {

    final ProcessScript _testScript = new ProcessScript('psgTrace.groovy')


    /**
     * Run script with empty DataContext => no documents!
     */
    @Test
    void test01() {
        DataContext dc = _testScript.run(DataContext.create())
        TestHelper.printTextDocuments(dc)
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
        TestHelper.printTextDocuments(dc)
    }
}
