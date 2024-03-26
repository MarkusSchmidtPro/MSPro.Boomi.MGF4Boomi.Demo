package processScript.inMemoryCache

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
class KeyValueTest {

    //final String TestDataDir  = 'src/ProcessScript/exceptionMessage/TestDocuments/'
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'


    /**
     * Test split single document with some Array items, using "(root)".
     */
    @Test
    void test01() {
        Map responseCache = [
                "Req01_Id" : '{ "Name" : "Markus Schmidt" }',
                "Req02_Id" : '{ "Name" : "Anna Müller" }',
        ]
        
        
        def ec = new ExecutionContexts()
        
        //
        // Fill keyValue cache
        //
        final ProcessScript setScript = new ProcessScript('/psg.MSPro_InMemory_SET.groovy', "InMemoryCache")
        setScript.run(DataContext.create([
                // Create document with response objects
                Document.fromText( "empty document 1", [ DDP_Key : responseCache.keySet()[0], DDP_Value : responseCache.values()[0] ] ),
                Document.fromText( "empty document 2", [ DDP_Key : responseCache.keySet()[1], DDP_Value : responseCache.values()[1] ] )
        ]), ec )
        
        //
        // retrieve from keyValue cache back to doc properties
        // Define the document property where we want to see the value from cache.
        //
        String valuePropertyName = "DDP_MyValue"
        ec.dynamicProcessProperties.DPP_ValuePropertyName = valuePropertyName
        final ProcessScript getScript = new ProcessScript('/psg.MSPro_InMemory_GET.groovy', "InMemoryCache")
        def dc = getScript.run(DataContext.create([
                // Create document with response objects
                Document.fromText( "empty document 1" , [ DDP_Key : responseCache.keySet()[0] ]),
                Document.fromText( "empty document 2" , [ DDP_Key : responseCache.keySet()[1] ])
        ]), ec )
        
        
        for (int docNo=0; docNo< dc.Documents.size(); docNo++) {
            
            def v = dc.Documents[ docNo]._dynamicDocumentProperties[ userDefinedPropertyBase + valuePropertyName]
            assert( responseCache.values()[docNo] == v)
        }
    }    
}
