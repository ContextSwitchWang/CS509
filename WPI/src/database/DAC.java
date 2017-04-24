package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// Database Access Class
public class DAC extends CallServer implements DAI {
	private String mUrlBase = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";
	public String getAirports(String ticketAgency)
	{
		
		String queryString = "?team=TeamA&action=list&list_type=airports";
		String url = mUrlBase+queryString; 
		String result = callServer(url,"GET","TeamA");
		return result;
	}
	
	public String getAirplanes(String ticketAgency)
	{
		String queryString = "?team=TeamA&action=list&list_type=airplanes";
		String url = mUrlBase+queryString;
		String result = callServer(url,"GET","TeamA");
		return result;
	}
	
	public String getFlightsDeparting(String ticketAgency, String airportCode, String day)
	{
		String queryString = "?team=TeamA&action=list&list_type=departing&airport=" + airportCode +"&day=" + day;
		String url = mUrlBase + queryString;
		String result = callServer(url,"GET","TeamA");
		return result;
	}
	
	public String getFlightsArriving(String ticketAgency, String airportCode, String day)
	{
		String queryString = "?team=TeamA&action=list&list_type=arriving&airport=" + airportCode +"&day=" + day;
		String url = mUrlBase + queryString;
		String result = callServer(url,"GET","TeamA");
		return result;
	}
	
	public String lock (String ticketAgency)
	{
		String queryString = "team=TeamA&action=lockDB";
		String url = mUrlBase + queryString;
		String result = callServer(url,"GET","TeamA");
		return result;
	}
	
	public String unlock (String ticketAgency)
	{
		String queryString = "team=TeamA&action=unlockDB";
		String url = mUrlBase + queryString;
		String result = callServer(url,"GET","TeamA");
		return result;
	}
	
	public String reserve (String ticketAgency, String xmlFlights) 
	{
		String queryString;
		try {
			queryString = "?team=TeamA&action=buyTickets&flightData=" + URLEncoder.encode(xmlFlights, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		String url = mUrlBase + queryString;
		
		lock(ticketAgency);
		String result = callServer(url,"GET","TeamA");
		unlock(ticketAgency);
		return result;

	}
	
	
	
}
