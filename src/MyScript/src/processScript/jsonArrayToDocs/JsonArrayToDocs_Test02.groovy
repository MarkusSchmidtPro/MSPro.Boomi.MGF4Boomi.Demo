package processScript.jsonArrayToDocs

import com.boomi.document.scripting.DataContext
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

/**
 * TEST serialization of a DataContext object.
 */
@TypeChecked
class JsonArrayToDocs_Test02 {

    final ProcessScript _testScript = new ProcessScript("psgJsonArrayToDocs.groovy", "jsonArrayToDocs")


    /**
     * Test split single document with some Array items, using "(root)".
     */
    @Test
    void test01() {
        DataContext dc = DataContext.create(
                [
                        Document.fromFile( new File("sampleArray.json")),
                        Document.fromFile( new File("sampleArray2.json")),
                        // File No3 does not contain an Array (=null)
                        // Edge test for the script!!
                        Document.fromFile( new File("sampleArray3.json"))
                ]
        )

        // Set the name of the Array element
        def ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_JsonArrayName = "MyArray"
        
        _testScript.run(dc, ec)

        assert dc.Documents.size() == 4
    }
}
