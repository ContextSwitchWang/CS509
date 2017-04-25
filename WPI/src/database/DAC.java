package database;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

// Database Access Class
public class DAC extends CallServer implements DAI {
	protected String mUrlBase = "http://cs509.cs.wpi.edu:8181/CS509.server/ReservationSystem";
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
		String queryString = "?team=TeamA&action=lockDB";
		String url = mUrlBase + queryString;
		String result = callServerPost(url,"TeamA", "");
		return result;
	}
	
	public String unlock (String ticketAgency)
	{
		String queryString = "?team=TeamA&action=unlockDB";
		String url = mUrlBase + queryString;
		String result = callServerPost(url,"TeamA", "");
		return result;
	}
	
	/**
	 * Reserve a seat on one or more connecting flights
	 * 
	 * The XML string identifying the reserveation is created by the calling client. 
	 * This method creates the HTTP POST request to reserve the fligt(s) as specified
	 * 
	 * @param team identifying the team making the reservation
	 * @param xmlReservation is the string identifying the reservation to make
	 * 
	 * @return true if SUCCESS code returned from server
	 */
	public String reserve(String team, String xmlReservation) {
		lock(team);
		String r = callServerPost(mUrlBase, team, "team=" + team + "&action=buyTickets" + "&flightData=" + xmlReservation);
		unlock(team);
		return r;
	}
	
}
