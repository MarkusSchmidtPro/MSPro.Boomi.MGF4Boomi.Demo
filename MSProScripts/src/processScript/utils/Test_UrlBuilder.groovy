package processScript.utils

import com.boomi.document.scripting.DataContext
import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test

@TypeChecked
class Test_UrlBuilder {
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

    @SourceURI
    URI _sourceUri
    final ProcessScript _processScript = new ProcessScript("psgUrlBuilder.groovy", _sourceUri)

    /**
     * Run script with two Strings as documents, no dynamic document properties.
     * However, DPP_DateTime 
     * and pp.MyProcess Property    
     *     - Val1 (int)     = 4711     
     *     - Val2 (String)  = "Markus Schmidt"     
     */
    @Test
    void test01() {
        def f = new File('psgUrlBuilder.groovy')
        ExecutionContexts ec = new ExecutionContexts()
        ec.dynamicProcessProperties.DPP_ValuePropertyName = "DPP_MyValue"
        ec.dynamicProcessProperties.DPP_PathTemplate = "{DPP_Path}"
        ec.dynamicProcessProperties.DPP_QueryTemplate = "value={DDP_V}"
        
        ec.dynamicProcessProperties.DPP_Path = "/root/contracts/get('abc def')"
        DataContext dc = _processScript.run(DataContext.create([
                Document.fromText("Document A",  [ DDP_V: "Markus Schmidt"] ),
                Document.fromText("Document B", [ DDP_V: "A & b"]) 
        ]), ec)

        def v = dc.Documents[ 0]._dynamicDocumentProperties[ userDefinedPropertyBase + "DPP_MyValue"]
        assert( "/root/contracts/get('abc%20def')?value=Markus+Schmidt" == v)
    }
}
