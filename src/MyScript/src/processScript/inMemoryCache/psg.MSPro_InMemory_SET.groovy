package ProcessScript.inMemoryCache

/// *************** COPY AND PASTE FROM HERE *****************
import com.boomi.execution.ExecutionUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

final String SCRIPT_NAME = "MSPro.InMemory SET"

/* ***********************************************************************

    Put [DDP_Value] to an in-memory cache using [DDP_Key] as a key that will be used later 
    to retrieve data back from cache.
    
        set( DDP_Key, DDP_Value, [DPP_CacheName="DPP_MSPro_Cache"])
    
    IN: DDP_Key     - The unique key under which the value is stored.
        DDP_Value   - The (string) value that is stored.
        DPP_CacheName [optional], Default: "DPP_MSPro_Cache"
                    - The name of the cache (aka the name of the DDP) 
                      where to store the key/value. 
        
    ----------------------------------------------------------------------
    2024-03-19  msc -   Created
************************************************************************** */

_logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

final String userDefinedPropertyBase = 'document.dynamic.userdefined.'

try {
    String cacheName = ExecutionUtil.getDynamicProcessProperty( "DPP_CacheName")
    if( cacheName == null || cacheName.isBlank()) cacheName = "DPP_MSPro_Cache"
    
    Map cache = _getCache( cacheName )
    
    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        Properties docProperties = dataContext.getProperties(docNo) as Properties
        InputStream documentStream = dataContext.getStream(docNo)

        // ------------------------------------------------------------------------
        
        cache.put(
            docProperties.getProperty( userDefinedPropertyBase + "DDP_Key"), 
            docProperties.getProperty( userDefinedPropertyBase + "DDP_Value")
        )

        // ------------------------------------------------------------------------
        
        dataContext.storeStream(documentStream, docProperties)
    }
    _saveCache( cacheName, cache)
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

static void _saveCache( String cacheName, Map cache)
{
    ExecutionUtil.setDynamicProcessProperty( cacheName, JsonOutput.toJson(cache), false)
}