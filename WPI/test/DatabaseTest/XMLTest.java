package DatabaseTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import database.XMLParser;
import airport.Airports;
import flight.Flights;

public class XMLTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAirports() {
		String xmlAirports = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <Airports>\n" + 
				"<Airport Code=\"ATL\" Name=\"Hartsfield-Jackson Atlanta International\">\n" + 
				"<Latitude>33.641045</Latitude> <Longitude>-84.427764</Longitude>\n" + 
				"</Airport> <Airport Code=\"ANC\" Name=\"Ted Stevens Anchorage International Airport\">\n" + 
				"<Latitude>61.176033</Latitude> <Longitude>-149.990079</Longitude>\n" + 
				"</Airport> <Airport Code=\"BOS\" Name=\"Logan International\"> <Latitude>42.365855</Latitude> <Longitude>-71.009624</Longitude> </Airport>\n" + 
				"</Airports>";
		Airports ans = XMLParser.parseAirports(xmlAirports);
		System.out.println(ans);
	}
	@Test
	public void testFlights(){
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?> <Flights>\n" + 
				"<Flight Airplane=\"747\" FlightTime=\"217\" Number=\"1560\">\n" + 
				"<Departure>\n" + 
				"<Code>AUS</Code> <Time>2016 May 10 02:47 GMT</Time>\n" + 
				"</Departure> <Arrival>\n" + 
				"<Code>BOS</Code> <Time>2016 May 10 06:24 GMT</Time>\n" + 
				"</Arrival> <Seating>\n" + 
				"<FirstClass Price=\"$132.54\">27</FirstClass> <Coach Price=\"$41.09\">206</Coach>\n" + 
				"</Seating>\n" + 
				"</Flight>\n" + 
				"</Flights>";
		Flights ans = XMLParser.parseFlights(xml);
		System.out.println(ans);
	}

}
