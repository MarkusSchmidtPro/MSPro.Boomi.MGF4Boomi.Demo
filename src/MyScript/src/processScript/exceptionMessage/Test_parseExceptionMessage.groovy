package processScript.exceptionMessage

import com.boomi.document.scripting.DataContext
import groovy.transform.SourceURI
import groovy.transform.TypeChecked
import msPro.mgf4boomi.Document
import msPro.mgf4boomi.ExecutionContexts
import msPro.mgf4boomi.ProcessScript
import org.junit.Test


/**
 * TEST serialization of a DataContext object.
 */
@TypeChecked
class Test_parseExceptionMessage {

    @SourceURI
    URI _sourceUri
    final ProcessScript _testScript = new ProcessScript("psgParseExceptionMessage.groovy", _sourceUri)
    final String userDefinedPropertyBase = 'document.dynamic.userdefined.'


    /**
     * Test split single document with some Array items, using "(root)".
     */
    @Test
    void test01() {
        //
        // Create and init [DPP_JsonArrayName]
        //
        def ec = new ExecutionContexts()
        //ec.initAddDynamicProcessProperty('DPP_JsonArrayName', '(root)')

        String testExceptionMessage = """
        Exception: ApplicationException
        {
            "statusCode" : 404,
            "message" : "Record not found!"
        } 
        """
        def dc = _testScript.run(DataContext.create([
                Document.fromText( "empty document", [ DDP_TryCatchMessage : testExceptionMessage ] )
        ]),ec )
        
        //
        // Check and print test result for each document
        //
        println("Documents after script execution")
        for (Document doc in dc.Documents) {
            def x = doc._dynamicDocumentProperties[ userDefinedPropertyBase + "DDP_exceptionType"]
            assert( "ApplicationException" == x)

            for( def p in doc._dynamicDocumentProperties) { println( "${p.key}='${p.value}'") }
        }
    }    
    
    @Test
    void test02() {
        String testExceptionMessage = """
            Custom Message: can"t parse argument number: subProcessNo; Caused by: For input string: "subProcessNo"
        """.trim()
        
        def dc = _testScript.run(DataContext.create([
                Document.fromText( "empty document", [ DDP_TryCatchMessage : testExceptionMessage ] )
        ]), new ExecutionContexts() )
        
        for (Document doc in dc.Documents) {
            def x = doc._dynamicDocumentProperties[ userDefinedPropertyBase + "DDP_exceptionType"]
            assert( x == null)
            for( def p in doc._dynamicDocumentProperties) { println( "${p.key}='${p.value}'") }
        }
    } 
}
