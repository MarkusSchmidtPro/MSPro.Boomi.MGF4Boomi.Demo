package processScript.jsonArrayToDocs

import com.boomi.document.scripting.DataContext
import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.TestFilesHelper
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

/**
 * TEST serialization of a DataContext object.
 */
@TypeChecked
class Test_JsonArrayToDocs_02 {

    @SourceURI
    URI _sourceUri
    final TestFilesHelper _testFiles = new TestFilesHelper( "TestDocuments", _sourceUri)
    final ProcessScript _processScript = new ProcessScript("psgJsonArrayToDocs.groovy", _sourceUri)

    /**
     * Test split single document with some Array items, using "(root)".
     */
    @Test
    void test01() {
        DataContext dc = DataContext.create(
                [
                        Document.fromFile(_testFiles.get("sampleArray.json")),
                        Document.fromFile(_testFiles.get("sampleArray2.json")),
                        // File No3 does not contain an Array (=null)
                        // Edge test for the script!!
                        Document.fromFile(_testFiles.get("sampleArray3.json"))
                ]
        )

        // Set the name of the Array element
        def ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_JsonArrayName = "MyArray"

        _processScript.run(dc, ec)

        assert dc.Documents.size() == 4
    }
}
