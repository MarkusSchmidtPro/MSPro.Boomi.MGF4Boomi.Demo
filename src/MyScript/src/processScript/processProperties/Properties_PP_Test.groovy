package processScript.processProperties

import com.boomi.document.scripting.DataContext
import msPro.mgf4boomi.tests.TestHelper
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Assert
import org.junit.Test

/**
 * TEST to demo MGF4Boomi functionality.
 */
@TypeChecked
class Properties_PP_Test {

    final ProcessScript _testScript = new ProcessScript('psgProperties_PP.groovy')

    // You may put all your Process Property IDs in a separate file so that 
    // you can use and share them in all your tests!

    // Process Properties are referenced by ID - Always and Only!
    // So that the script can work properly the Ids provided here 
    // must match the IDs of the Properties on the platform.
    // This is, because the Script will have to use the same Ids,
    // and if you want to copy and paste the script code, the Ids
    // on the platform and in our emulation must be the same!
    final String PROCESS_PROPERTY_COMPONENT_ID = "8fb41f63-a988-4778-8cc8-0144f30ace81"
    final String VAL1_ID = "eea9e988-cb14-4a84-ba37-ee455451a741"
    final String VAL2_ID = "2c68fb60-8431-46cc-9da9-cbe10d446a0e"


    /**
     * Run script with two Strings as documents, no dynamic document properties.
     * However, DPP_DateTime 
     * and pp.MyProcess Property    
     *     - Val1 (int)     = 4711     
     *     - Val2 (String)  = "Markus Schmidt"     
     */
    @Test
    void test01() {

        int var1 = 4711
        
        ExecutionContexts ec = new ExecutionContexts()
        ec.processProperties[PROCESS_PROPERTY_COMPONENT_ID] = [
                // Wrap key in parenthesis so that the variables (Ids) are taken
                // and not the text as a string
                (VAL1_ID)   : var1,
                (VAL2_ID)   : "Markus Schmidt"
        ]

        DataContext dc = _testScript.run(DataContext.create([
                Document.fromText("Document A"),
                Document.fromText("Document B")
        ]), ec)

        //
        // Print all documents
        // And check the Script has set all properties according to our expectations
        //
        TestHelper.printTextDocuments(dc)

        // Should have been incremented
        Assert.assertEquals(ec.processProperties[PROCESS_PROPERTY_COMPONENT_ID][VAL1_ID], var1 + 1)
    }


    @Test
    void test_fail() {        // Finally, simulate an assertion
        assert false,  "Cannot be met"
    }
}
