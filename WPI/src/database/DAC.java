package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Database Access Class
public class DAC implements DAI {
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
	
	public String lock (String ticketAgency)
	{
		String queryString = "";
		return "blah";
	}
	
	public String unlock (String ticketAgency)
	{
		return "blah";
	}
	
	public String reserve (String ticketAgency, String xmlFlights) 
	{
		return "blah";
	}
	
	public String getTimeOffset(String latitude, String longitude)
	{
		String url = "https://maps.googleapis.com/maps/api/timezone/xml?location="+latitude+","+longitude+"&timestamp=1331161200&key=AIzaSyDWI6Y8TSzKLtiHyQnTwjxtCXcNTMX7NLM";
		String result = callServer(url, "GET", "Mozilla/5.0");
		return result;
	}
	
	private String callServer(String urlString, String method, String userAgent)
	{
		
		URL url;
		HttpURLConnection connection;
		BufferedReader reader;
		String line;
		StringBuffer result = new StringBuffer();
		try
		{
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setRequestProperty("User-Agent", userAgent);

			/**
			 * If response code of SUCCESS read the XML string returned
			 * line by line to build the full return string
			 */
			int responseCode = connection.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				String encoding = connection.getContentEncoding();
				encoding = (encoding == null ? "UTF-8" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
}
