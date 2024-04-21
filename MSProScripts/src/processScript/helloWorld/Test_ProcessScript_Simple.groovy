package processScript.helloWorld

import com.boomi.document.scripting.DataContext
import groovy.transform.SourceURI
import msPro.mgf4boomi.tests.TestHelper
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

/**
 * Demo MGF4Boomi functionality.
 */
@TypeChecked
class Test_ProcessScript_Simple {

    @SourceURI
    URI _sourceUri
    final ProcessScript _processScript = new ProcessScript("psgTrace.groovy", _sourceUri)


    /**
     * Run script with empty DataContext => no documents!
     */
    @Test
    void test01() {
        DataContext dc = _processScript.run(DataContext.create())
        TestHelper.printTextDocuments(dc)
    }


    /**
     * Run script with two Strings as documents!
     */
    @Test
    void test02() {
        DataContext dc = _processScript.run(DataContext.create([
                Document.fromText("Document A"),
                Document.fromText("Document B"),
        ]))
        TestHelper.printTextDocuments(dc)
    }
}
