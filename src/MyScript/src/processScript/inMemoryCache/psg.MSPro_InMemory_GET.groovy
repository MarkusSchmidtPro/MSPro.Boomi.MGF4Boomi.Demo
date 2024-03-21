package ProcessScript.inMemoryCache

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil
import groovy.json.JsonSlurper

final String SCRIPT_NAME = "MSPro.InMemory GET"

/* ***********************************************************************

    Get from in-memory cache using [DDP_Key] and write result to [DDP_Value].
    
        DDP_Value = get( DDP_Key, [DPP_CacheName="DPP_MSPro_Cache"])
    
    IN: DDP_Key     - The unique key under which the value is stored.
        DDP_Value   - The (string) value that is stored.
        
        PROCESS Properties:
        DPP_CacheName       [optional], Default: "DPP_MSPro_Cache"
            The name of the cache (aka the name of the DDP) 
            where to store the key/value. 
        DPP_ValueProperty   [optional], Default: "DDP_Value"
            A dynamic document property name that gets the value. 
                      
    ----------------------------------------------------------------------
    2024-03-19  msc -   Created
************************************************************************** */

final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

_logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

try {
    String cacheName = ExecutionUtil.getDynamicProcessProperty( "DPP_CacheName")
    if( cacheName == null || cacheName.isBlank()) cacheName = "DPP_MSPro_Cache"    
    
    String valuePropertyName = ExecutionUtil.getDynamicProcessProperty( "DPP_ValuePropertyName")
    if( valuePropertyName == null || valuePropertyName.isBlank()) valuePropertyName = "DDP_Value"
    
    Map cache = _getCache( cacheName )
    
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        Properties docProperties = dataContext.getProperties(docNo) as Properties
        InputStream documentStream = dataContext.getStream(docNo)

        // ------------------------------------------------------------------------
        
        String key = docProperties.getProperty( userDefinedPropertyBase + "DDP_Key")
        String value = cache[key] as String
        docProperties.setProperty( userDefinedPropertyBase + valuePropertyName, value)
        //_logger.fine( "${key}='${value}'" )
        
        // ------------------------------------------------------------------------
        dataContext.storeStream(documentStream, docProperties)
    }
}
catch (Exception e) {
    _logger.severe(e.message)
    throw e
}

// =================================================
// -------------------- LOCALS ---------------------
// =================================================

static Map _getCache(  String cacheName)
{
    final def js = new JsonSlurper()

    String dpp = ExecutionUtil.getDynamicProcessProperty( cacheName)
    return  dpp != null ? js.parseText(dpp) as Map : [:]
}