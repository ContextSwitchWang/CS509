package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TimeConversion  extends CallServer implements TimeConversionAPI {
	public String getTimeOffset(String latitude, String longitude)
	{
		String url = "https://maps.googleapis.com/maps/api/timezone/xml?location="+latitude+","+longitude+"&timestamp=1331161200&key=AIzaSyDWI6Y8TSzKLtiHyQnTwjxtCXcNTMX7NLM";
		String result = callServer(url, "GET", "Mozilla/5.0");
		return result;
	}
}
