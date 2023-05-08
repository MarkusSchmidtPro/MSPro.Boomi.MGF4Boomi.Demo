package ProcessScript

import com.boomi.execution.ExecutionUtil
import org.jdom.Document
import org.jdom.Element
import org.jdom.input.SAXBuilder
import org.jdom.output.XMLOutputter
import org.jdom.xpath.XPath

/// *************** COPY AND PASTE FROM HERE *****************

final String SCRIPT_NAME = "psgUC2"


/** **************************************************************************
 * <h1>Einride Groovy Script for Use Case 2.1</h1>
 *
 * - create a new list of [ShipmentStops]
 * - A [ShipmentStop] can have 1..n [Shipment]s
 * - Each [Shipment] has one [ShipmentGid]
 * - Each [Shipment] can have 2..n [ShipmentStop]s
 *
 * - Find all [ShipmentStop]s across all [Shipment]s
 * - build a distinct list (array) of [ShipmentStop]s --> result List
 * - where each [ShipmentStop] refers to a list of [ShipmentGid]s it was listed
 *
 * A [ShipmentStop] is district (same stop) when 
 * [Shipment[].ShipUnit[].ShipToLocation.LocationGid.Gid.Xid] 

 ## To.Be
 '''JSON
 "stops" : [
 {
     "address" : {
     "recipient" : "RDC NÜRNBERG HAFEN",
     "line1" : "Preßburger Straße 4",
     "postalCode" : "90451",
     "city" : "NÜRNBERG HAFEN",
     "regionCode" : "DEU"
     },
     "stopType" : "PICKUP",
     "requestedStartTime" : "20230417120000",
     "requestedEndTime" : "20230417130000",
     "shipmentExternalReferenceIds" : [   "018958416"   ]
 },
 {
     "address" : {
     "recipient" : "DE - PLATFORM SD - STRULLENDORF",
     "line1" : "Siemensstrasse 19",
     "postalCode" : "96129",
     "city" : "Strullendorf",
     "regionCode" : "DEU"
     },
     "requestedStartTime" : "20230417140712",
     "requestedEndTime" : "20230417140712",
     "shipmentExternalReferenceIds" : [ "018958416" ]
 }
 ]
 '''
 <h2>History</h2>
 <pre>
 Date        Author  Note
 -------------------------------
 2023-05-05  msc     -   Created
 **************************************************************************
 * </pre>
 * */

class Stop {
    String LocationXid
    Element Address
    Element ShipmentStop
    HashSet<String> ShipmentXids = []
}

final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

try {

    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {

        final Properties props = dataContext.getProperties(docNo) as Properties
        Document xSource = _getXmlDocument(dataContext, docNo)
        def ns = xSource.content[0].namespace

        Document resultDoc = new Document()
        Element root = new Element("root", ns)
        resultDoc.addContent(root)


        XPath x = XPath.newInstance("//x:Transmission//x:TransmissionBody[1]//x:GLogXMLElement[1]//x:ShipmentGroup[1]//x:Shipment[1]")
        x.addNamespace("x", ns.uri)
        
        //
        // PRE-CONDITION Shipment is single node!
        //
        Element eShipment = x.selectSingleNode(xSource) as Element

        def eShipmentXid = eShipment.getChild("ShipmentHeader", ns).getChild("ShipmentGid", ns).getChild("Gid", ns).getChild("Xid", ns) as Element
        String shipmentXid = eShipmentXid.value

        Map<String, Element> locations = [:]    // Map with unique locations
        for (def location in eShipment.getChildren("Location", ns)) {

            def locationXid = location.getChild("LocationGid", ns).getChild("Gid", ns).getChild("Xid", ns) as Element
            location.parent = null
            locations[locationXid.value as String] = location as Element
        }
        
        //
        // Iterate through all ShipmentStop (over all Units in current Shipment!)
        // build a unique list (by LocationGid.Xid)
        //
        List eShipmentStops= eShipment.getChildren("ShipmentStop", ns)
        eShipmentStops.unique { eShipmentStop1, eShipmentStop2 ->
            String v1 = eShipmentStop1.getChild("LocationGid", ns).getChild("Gid", ns).getChild("Xid", ns).value
            String v2 = eShipmentStop2.getChild("LocationGid", ns).getChild("Gid", ns).getChild("Xid", ns).value
            return v1.compareTo(v2)
        }


        //
        // Combine unique shipToLocations with Location address 
        //
        Element eStops = new Element("Stops")

        for (def shipmentStop in eShipmentStops) {
            shipmentStop.parent=null
            
            String stopLocationId = eShipmentStop1.getChild("LocationGid", ns).getChild("Gid", ns).getChild("Xid", ns).value
            eStops.addContent {
                LocationXid = stopLocationId
                Address = locations[ stopLocationId]
                ShipmentStop = shipmentStop
                ShipmentXids.add(shipmentXid)
            }
        }
        root.addContent(eStops)


            Element eLocations = new Element("Locations")
            root.addContent(eLocations)
            eLocations.addContent(locations.collect { it.value })

            _setXmlDocument(dataContext, resultDoc, props)
        }   // next Document

        // ----------------

        _logger.info('<<< End Script')
    }
    catch (Exception e) {
        _logger.severe(e.message)
        throw e
    }


// =================================================
// ------- Business / Transformation Logic ---------
// =================================================

// =================================================
// -------------------- LOCALS ---------------------
// =================================================

    static Document _getXmlDocument(dataContext, int docNo) {
        InputStream is = dataContext.getStream(docNo)
        SAXBuilder builder = new SAXBuilder()
        return builder.build(is)
    }

    static void _setXmlDocument(dataContext, Document xDoc, Properties props) {
        final XMLOutputter op = new XMLOutputter()
        InputStream newDocumentStream = new ByteArrayInputStream(op.outputString(xDoc).getBytes("UTF-8"))
        dataContext.storeStream(newDocumentStream, props)
    }

    static String _getTextDocument(dataContext, int docNo) {
        InputStream documentStream = dataContext.getStream(docNo)
        return documentStream.getText("UTF-8")
    }


    static void _setTextDocument(dataContext, String value, Properties props) {
        InputStream newDocumentStream = new ByteArrayInputStream(value.getBytes("UTF-8"))
        dataContext.storeStream(newDocumentStream, props)
    }
