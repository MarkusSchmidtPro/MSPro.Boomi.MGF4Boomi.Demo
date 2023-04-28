package MapScript

import com.boomi.execution.ExecutionUtil

/*
IN: PaymentStatus|PaymentType
OUT: StageName

IN: PaymentStatus
    == “Payment Accepted”
        If PaymentType in the file is one of 
            [CAF|CAF Credit Card|Charities Trust|KKL Charity Voucher|Other Vouchers|South West Charitable Giving (SWCG)|Stewardship|Aschimochi Aid Co|Charitable Giving] 
        then  
            StageName = 'Pledged'
        else  
            StageName = 'Received'
    
    == “Payment Rejected”
            StageName = “Declined”
 */

logger = ExecutionUtil.getBaseLogger()
logger.fine("PaymentStatus ${PaymentStatus}|PaymentType=${PaymentType}")

switch( PaymentStatus ){
    case 'Payment Accepted':
        final String x = '[CAF|CAF Credit Card|Charities Trust|KKL Charity Voucher|Other Vouchers|South West Charitable Giving (SWCG)|Stewardship|Aschimochi Aid Co|Charitable Giving]'
        StageName = x.contains(PaymentType) ? 'Pledged' : 'Received'
        break;
    case 'Payment Rejected':
        StageName = 'Declined'
        break;
    default:
        throw "Invalid Payment Status: ${PaymentStatus}"
}
