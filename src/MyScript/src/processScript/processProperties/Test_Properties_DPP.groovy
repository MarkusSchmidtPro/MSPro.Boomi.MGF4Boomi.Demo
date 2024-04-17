package processScript.processProperties

import com.boomi.document.scripting.DataContext
import groovy.transform.SourceURI
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
class Test_Properties_DPP {

    @SourceURI
    URI _sourceUri
    final ProcessScript _processScript = new ProcessScript("psgProperties_DPP.groovy", _sourceUri)

    /**
     * Run script with two Strings as documents, no dynamic document properties.
     * However, DPP_DateTime 
     * and pp.MyProcess Property    
     *     - Val1 (int)     = 4711     
     *     - Val2 (String)  = "Markus Schmidt"     
     */
    @Test
    void test01() {
        def f = new File('psgProperties_DPP.groovy')
        //
        // Add Dynamic Process Properties to the execution context.
        // and pass it to the script execution.
        //
        ExecutionContexts ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_DateTime = new Date()
        
        DataContext dc = _processScript.run(DataContext.create([
                Document.fromText("Document A"),
                Document.fromText("Document B")
        ]), ec)

        TestHelper.printTextDocuments(dc)

        assert ec.dynamicProcessProperties.DPP_Out == true, "DPP_out does not meet expectations!"
        assert ec.dynamicProcessProperties.DPP_Out, "DPP_out does not meet expectations!"
    }
}
