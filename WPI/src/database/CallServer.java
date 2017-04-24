package database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CallServer {
	protected String callServer(String urlString, String method, String userAgent)
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
				encoding = (encoding == null ? "ISO-8859-1" : encoding);

				reader = new BufferedReader(new InputStreamReader(inputStream, encoding));
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
				reader.close();
			}
		}
		catch (IOException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
}
