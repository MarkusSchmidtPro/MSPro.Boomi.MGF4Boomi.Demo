package MapScript


import groovy.transform.TypeChecked
import msPro.mgf4boomi.ExecutionUtilContexts
import msPro.mgf4boomi.MapScript
import org.junit.Test

@TypeChecked
class msgPaymentStatusTest {

    //
    // Specify Test Script and Testdata directories.
    //
    //final String TESTDATA_DIR = 'TestData/MSGJsonToHtml'
    final MapScript _script = new MapScript('msgPaymentStatus.groovy')


    @Test
    void test01() {
        Map inputParameters = [PaymentStatus: 'Payment Accepted', PaymentType: 'CAF Credit Card']
        String expected = 'Pledged'
        Map outputParameters = _script.run(ExecutionUtilContexts.empty(), inputParameters)
        assert outputParameters.StageName == expected
    }

    @Test
    void test01a() {
        Map inputParameters = [PaymentStatus: 'Payment Accepted', PaymentType: 'other']
        String expected = 'Received'
        Map outputParameters = _script.run(ExecutionUtilContexts.empty(), inputParameters)
        assert outputParameters.StageName == expected
    }

    @Test
    void test01b() {
        Map inputParameters = [PaymentStatus: 'Payment Rejected', PaymentType:'']
        String expected = 'Declined'
        Map outputParameters = _script.run(ExecutionUtilContexts.empty(), inputParameters)
        assert outputParameters.StageName == expected
    }
}
