package database;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import airport.Airport;
import airport.Airports;
import flight.Flights;
import flight.Flight;


public class XMLParser {
	/**
	 * @param xml string to generate flights from
	 * @return the flights generated from XML
	 * @throws NullPointerException to be consistent
	 */
	public static Flights parseFlights(String xml) throws NullPointerException {
		Flights ans = new Flights();
		Document doc = buildDomDoc(xml);
		NodeList nodes = doc.getElementsByTagName("Flight");	
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);
			Flight e = buildFlight(element);	
			if (e.isValid()) {
				ans.add(e);
			}
		}
		return ans;
	}
	
	/**
	 * Builds collection of airports from airports described in XML
	 * 
	 * Parses an XML string to read each of the airports and adds each valid airport 
	 * to the collection. The method uses Java DOM (Document Object Model) to convert
	 * from XML to Java primitives.
	 * 
	 * @param xmlAirports XML string containing set of airports 
	 * @return [possibly empty] collection of Airports in the xml string
	 * @throws NullPointerException included to keep signature consistent with other addAll methods
	 * 
	 * the xmlAirports string adheres to the format specified by the server API
	 */
	public static Airports parseAirports (String xmlAirports) throws NullPointerException {
		Airports airports = new Airports();
		
		// Load the XML string into a DOM tree for ease of processing
		// then iterate over all nodes adding each airport to our collection
		Document docAirports = buildDomDoc (xmlAirports);
		NodeList nodesAirports = docAirports.getElementsByTagName("Airport");
		
		for (int i = 0; i < nodesAirports.getLength(); i++) {
			Element elementAirport = (Element) nodesAirports.item(i);
			Airport airport = buildAirport (elementAirport);
			
			if (airport.isValid()) {
				airports.add(airport);
			}
		}
		
		return airports;
	}
	
	
	
	/**
	 * Creates an Airport object from a DOM node
	 * 
	 * Processes a DOM Node that describes an Airport and creates an Airport object from the information
	 * @param nodeAirport is a DOM Node describing an Airport
	 * @return Airport object created from the DOM Node representation of the Airport
	 * 
	 * nodeAirport is of format specified by CS509 server API
	 */
	static private Airport buildAirport (Element nodeAirport) {
		Airport airport = new Airport();
		// The airport element has attributes of Name and 3 character airport code
		Element elementAirport = nodeAirport;
		airport.name = elementAirport.getAttribute("Name");
		airport.Code = elementAirport.getAttribute("Code");
		
		// The latitude and longitude are child elements
		Element elementLatLng;
		elementLatLng = (Element)elementAirport.getElementsByTagName("Latitude").item(0);
		airport.Latitude = Double.parseDouble(getCharacterDataFromElement(elementLatLng));
		
		elementLatLng = (Element)elementAirport.getElementsByTagName("Longitude").item(0);
		airport.Longitude = Double.parseDouble(getCharacterDataFromElement(elementLatLng));
		return airport;
	}
	
	/**
	 * @param element to build flight from
	 * @return a Flight constructed from element
	 */
	private static Flight buildFlight(Element element) {
		Flight ans = new Flight();
		ans.Airplane = element.getAttribute("Airplane");
		ans.FlightTime = element.getAttribute("FlightTime");
		ans.Number = element.getAttribute("Number");
		
		Element seating = (Element)element.getElementsByTagName("Seating").item(0);
		Element first = (Element)seating.getElementsByTagName("FirstClass").item(0);
		ans.PriceFirstclass = first.getAttribute("Price");
		ans.SeatsFirstclass = Integer.parseInt(getCharacterDataFromElement(first));
		Element coach = (Element)seating.getElementsByTagName("Coach").item(0);
		ans.PriceCoach = coach.getAttribute("Price");
		ans.SeatsCoach = Integer.parseInt(getCharacterDataFromElement(coach));
		

		Element time;
		Element Code;
		Element Time;
		
		time = (Element)element.getElementsByTagName("Departure").item(0);
		Code = (Element)time.getElementsByTagName("Code").item(0);
		Time = (Element)time.getElementsByTagName("Time").item(0);
		ans.CodeDepart = getCharacterDataFromElement(Code);
		ans.TimeDepart = getCharacterDataFromElement(Time);
		
		time = (Element)element.getElementsByTagName("Arrival").item(0);
		Code = (Element)time.getElementsByTagName("Code").item(0);
		Time = (Element)time.getElementsByTagName("Time").item(0);
		ans.CodeArrival = getCharacterDataFromElement(Code);
		ans.TimeArrival = getCharacterDataFromElement(Time);
		return ans;
	}

	/**
	 * Builds a DOM tree form an XML string
	 * 
	 * Parses the XML file and returns a DOM tree that can be processed
	 * 
	 * @param xmlString XML String containing set of objects
	 * @return DOM tree from parsed XML or null if exception is caught
	 */
	static private Document buildDomDoc (String xmlString) {
		/**
		 * load the xml string into a DOM document and return the Document
		 */
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource inputSource = new InputSource();
			inputSource.setCharacterStream(new StringReader(xmlString));
			
			return docBuilder.parse(inputSource);
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retrieve character data from an element if it exists
	 * 
	 * @param e is the DOM Element to retrieve character data from
	 * @return the character data as String [possibly empty String]
	 */
	private static String getCharacterDataFromElement (Element e) {
		Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	        CharacterData cd = (CharacterData) child;
	        return cd.getData();
	      }
	      return "";
	}
}
