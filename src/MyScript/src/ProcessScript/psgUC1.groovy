package ProcessScript

import com.boomi.execution.ExecutionUtil
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/// *************** COPY AND PASTE FROM HERE *****************

final String SCRIPT_NAME = "psgUC1"


/** **************************************************************************

 <h1>Einride Groovy Script for Use Case 1</h1>

 The script works per document. It does NOT combine documents!
 <p>
 A Groovy Script based on the provided AS-IS JSON files, to 
 combine [stops] elements: 
 <p>
 [stops] elements are combined when they are the same.
 "The same" means: 
 a) same [address] object (full JSON match of all elements)
 b) the same [requestedStartTime]
 <p>
 [shipmentExternalReferenceIds] are aggregated in an Array
 (no duplicate checking, new array item for each 'source' stop)
 List will be ordered by requestedStartTime (using string sorting)
 <p> 
 <h2>Example File AS-IS</h2>
 <pre>
 {
 "externalReferenceId": "001-TSO-S10000100484",
 "preliminaryShipments": [  // not of interest
 ],
 "stops": [
 {
 "address": {
 "recipient": "IKEA DC TORSVIK JOENKOEPING",
 "line1": "MOEBELVAEGEN 14",
 "postalCode": "556 52",
 "city": "JOENKOEPING",
 "regionCode": "SE"
 },
 "stopType": "loading",
 "requestedStartTime": "2023-04-04 11:00:00",
 "requestedEndTime": "2023-04-04 11:00:00",
 "shipmentExternalReferenceIds": [
 "008-DT-230403014704|001-TSO-S10000100484"
 ]
 },
 {
 "address": {
 "recipient": "IKEA GOETEBORG - KALLERED KALLERED",
 "line1": "EKENLEDEN 2",
 "postalCode": "42836",
 "city": "KALLERED",
 "regionCode": "SE"
 },
 "stopType": "unloading",
 "requestedStartTime": "2023-04-05 07:15:00",
 "requestedEndTime": "2023-04-05 07:15:00",
 "shipmentExternalReferenceIds": [
 "008-DT-230403014704|001-TSO-S10000100484"
 ]
 }]
 }
 <pre/>

 <h2>History</h2>
 <pre>
 Date        Author  Note
 -------------------------------
 2023-05-04  msc     -   Created
 **************************************************************************
 * </pre>
 * */


final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

try {
    final JsonSlurper slurper = new JsonSlurper()

    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {
        // -------------------------------------------------
        // Get Boomi Document as text and parse into JSON
        // -------------------------------------------------
        String textDocument = _getTextDocument(dataContext, docNo)
        def jsonObject = slurper.parseText(textDocument)
        // -------------------------------------------------

        def toBeStops = _transformJson(jsonObject)
        jsonObject.stops = toBeStops
            
        // -------------------------------------------------
        // Convert JSON object back into String
        // and write back into Boomi Document Stream
        // -------------------------------------------------
        textDocument = JsonOutput.toJson(jsonObject)
        _setTextDocument(dataContext, textDocument, dataContext.getProperties(docNo) as Properties)
    }

    _logger.info('<<< End Script')
}
catch (Exception e) {
    _logger.severe(e.message)
    throw e
}

// =================================================
// ------- Business / Transformation Logic ---------
// =================================================

/**
 * Transform JSON
 * @param asIsJson
 * @return toBeStops
 */
static def _transformJson(def asIsJson) {
    Object[] stops = asIsJson.stops // LazyMap
    List result = []

    // Create a map with two levels
    // 0 : equals address
    // 1 : equals address + time
    // All element on level1 are unique per definition
    // and will be addded to the result collection
    def stopAddresses = stops.groupBy([
            { stop -> stop.address },
            { stop -> stop.requestedStartTime }
    ])

    for (def stopAddress in stopAddresses.values()) {
        for (ArrayList stopsSamePlaceAndTime in stopAddress.values()) {

            int index = 0
            def currentStop
            for (def stop in stopsSamePlaceAndTime) {

                if (index == 0) {
                    // The first item is added to the result list as-is
                    result.add(stop)
                    currentStop = stop
                } else {
                    // for all other stops (with the same address and startTime)
                    // shipmentExternalReferenceIds is appended to the first stopsSamePlaceAndTime
                    currentStop.shipmentExternalReferenceIds.addAll stop.shipmentExternalReferenceIds
                }
                index++
            }
        }
    }
    
    return result
}


// =================================================
// -------------------- LOCALS ---------------------
// =================================================

static String _getTextDocument(dataContext, int docNo) {
    InputStream documentStream = dataContext.getStream(docNo)
    return documentStream.getText("UTF-8")
}

static void _setTextDocument(dataContext, String value, Properties props) {
    InputStream newDocumentStream = new ByteArrayInputStream(value.getBytes("UTF-8"))
    dataContext.storeStream(newDocumentStream, props)
}

