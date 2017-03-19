package DatabaseTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import database.XMLParser;
import airport.Airports;

public class XMLTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
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

}
