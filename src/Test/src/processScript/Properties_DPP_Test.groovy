
import com.boomi.document.scripting.DataContext
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import msPro.mgf4boomi.tests.TestHelper
import org.junit.Test

/**
 * TEST to demo MGF4Boomi functionality.
 */
@TypeChecked
class Properties_DPP_Test {

    final ProcessScript _testScript = new ProcessScript('psgProperties_DPP.groovy')

    /**
     * Run script with two Strings as documents, no dynamic document properties.
     * However, DPP_DateTime 
     * and pp.MyProcess Property    
     *     - Val1 (int)     = 4711     
     *     - Val2 (String)  = "Markus Schmidt"     
     */
    @Test
    void test01() {

        //
        // Add Dynamic Process Properties to the execution context.
        // and pass it to the script execution.
        //
        ExecutionContexts ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_DateTime = new Date()
        
        DataContext dc = _testScript.run(DataContext.create([
                Document.fromText("Document A"),
                Document.fromText("Document B")
        ]), ec)

        TestHelper.printTextDocuments(dc)

        assert ec.dynamicProcessProperties.DPP_Out == true, "DPP_out does not meet expectations!"
        assert ec.dynamicProcessProperties.DPP_Out, "DPP_out does not meet expectations!"
    }
}
