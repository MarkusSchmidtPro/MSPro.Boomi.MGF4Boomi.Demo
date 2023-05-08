package ProcessScript

import com.boomi.execution.ExecutionUtil
import groovy.xml.MarkupBuilder
import groovy.xml.StreamingMarkupBuilder
import groovy.xml.XmlUtil
import org.jdom.Document
import org.jdom.Element
import org.jdom.input.SAXBuilder
import org.jdom.output.XMLOutputter
import org.jdom.xpath.XPath

import javax.xml.xpath.XPathConstants


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
    Node Address
    Node ShipmentStop
    HashSet<String> ShipmentXids = []
}

final _logger = ExecutionUtil.getBaseLogger()
_logger.finest('>>> Script start ' + SCRIPT_NAME)

try {

    for (int docNo = 0; docNo < dataContext.getDataCount(); docNo++) {

        final Properties props = dataContext.getProperties(docNo) as Properties
        Document xSource = _getXmlDocument(dataContext, docNo)
        
        XPath x = XPath.newInstance("//x:Transmission//x:TransmissionBody[1]//x:GLogXMLElement[1]//x:ShipmentGroup[1]//x:Shipment[1]")
        x.addNamespace("x", xSource.content[0].namespace.uri)//"
        // Select a single node and grab the actual value
        def eleMyElement = x.selectSingleNode(xSource)
        //if (eleMyElement != null) myValue = eleMyElement.getText()

        eleMyElement.getParentElement().detach()
        eleMyElement.parent=null
        Document resultDoc = new Document()
        resultDoc.addContent( eleMyElement)
        
        _setXmlDocument(dataContext, resultDoc, props)
        
        
    
       /*
               Document xDoc = new Document();
          xDoc.setRootElement(new Element("company"));
    
          Element staff = new Element("staff");
          // add xml attribute
          staff.setAttribute("id", "1001");
    
          staff.addContent(new Element("name").setText("mkyong"));
          staff.addContent(new Element("role").setText("support"));
          staff.addContent(new Element("salary")
                  .setAttribute("curreny", "USD").setText("5000"));
    
          // add xml comments
          staff.addContent(new Comment("for special characters like < &, need CDATA"));
    
          // add xml CDATA
          staff.addContent(new Element("bio")
                  .setContent(new CDATA("HTML tag <code>testing</code>")));
              
              
               //Document resultDoc = transformer.transform( xdoc)*/
        //Document resultDoc = xSource


        //Shipment[].ShipUnit[].ShipTransmissionBody.GLogXMLElement.ShipmentGroupLocation.LocationGid.Gid.Xid
        // ----------------

        def xmlkString = _getTextDocument(dataContext, docNo)
      /*  def xml1 = new XmlSlurper().parseText(xmlkString)
        xml1.appendNode { foo(bar: "bar value") }
        def writer1 = new StringWriter()
        def builder = new StreamingMarkupBuilder()
        writer1 << builder.bind { mkp.yield xml1 }
        XmlUtil.serialize(xml1, writer1)
        String y = writer1.toString()*/


        def transmission = new XmlSlurper().parseText(_getTextDocument(dataContext, docNo))
        def shipmentGroup = transmission.TransmissionBody.GLogXMLElement.ShipmentGroup

        NodeBuilder root = new NodeBuilder()

        Map<String, Node> result = [:]

        // Compile unique locations
        Map<String, Node> locations = [:]
        for (def shipment in shipmentGroup.Shipment) {

            for (def locationNode in shipment.Location) {
                locations[locationNode.LocationGid.Gid.Xid[0] as String] = locationNode
                root.appendNode(locationNode)
                //eLoc.addContent((new Element("locationNode")).setText(locationNode.LocationName.value))
            }
        }

        def build = new StreamingMarkupBuilder()
        def smb = build.bind { mkp.yield transmission }

        def sw = new StringWriter()
        MarkupBuilder mb = new MarkupBuilder(sw)
        mb { transmission }
        String xx = XmlUtil.serialize(sw.toString())

        // shipment/Location[22]/LocationGid[1]/Gid[1]
        XMLOutputter xmlOutputter = new XMLOutputter()
        xmlOutputter.output(locations, System.out)

        /*  for (def shipment in shipmentGroup.Shipment) {
  
              String shipmentXid = shipment.ShipmentHeader.ShipmentGid.Gid.Xid[0].value()
              List shipmentUnits = shipment.ShipUnit
  
              *//*
              NodeList nl = shipmentUnits[0].ShipToLocation.LocationGid.Gid.Xid
              Node n = nl[0]
              String nValue = n.value()
              *//*
  
              List shipmentStops = shipmentUnits.unique { node1, node2 ->
                  String v1 = node1.ShipToLocation.LocationGid.Gid.Xid[0].value()
                  String v2 = node2.ShipToLocation.LocationGid.Gid.Xid[0].value()
                  return v1.compareTo(v2)
              }
  
              for (Node shipmentStop in shipmentStops) {
                  String stopLocationId = shipmentStop.ShipToLocation.LocationGid.Gid.Xid[0].value()
                  result[stopLocationId] = new Stop()
                  result[stopLocationId].LocationXid = stopLocationId
                  result[stopLocationId].Address = locations[stopLocationId]
                  result[stopLocationId].ShipmentStop = shipmentStop
                  result[stopLocationId].ShipmentXids.add(shipmentXid)
              }
          }
  
          def parser = new XmlParser()
          def response = new Document()
          def numberOfResults = parser.createNode(
                  null,
                  new QName("numberOfResults"),
                  [:]
          )
  
          numberOfResults.value = result
  
          _setTextDocument(dataContext, XmlUtil.serialize(numberOfResults), props)*/
        //_setTextDocument(dataContext, JsonOutput.toJson(result.values()), props)

        def writer = new StringWriter()
        MarkupBuilder xml = new MarkupBuilder(writer)

        xml.root() { locations.each() }

        _setXmlDocument(dataContext, eLoc, props)

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
