package database;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	protected String callServerPost(String urlString, String userAgent, String params){
		URL url;
		HttpURLConnection connection;

		try {
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");

			System.out.println("\nSending 'POST' to ReserveFlights");
			//System.out.println("\nSending " + params);
			
			connection.setDoOutput(true);
			connection.setDoInput(true);
			
			DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
			writer.writeBytes(params);
			writer.flush();
			writer.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("\nSending 'POST' to ReserveFlights");
			System.out.println(("\nResponse Code : " + responseCode));

			if ((responseCode >= 200) && (responseCode <= 299)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				System.out.println(response.toString());
				return "true";
			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				StringBuffer response = new StringBuffer();

				while ((line = in.readLine()) != null) {
					response.append(line);
				}
				in.close();

				System.out.println(response.toString());
				return "false";
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return "false";
		}
		catch (Exception ex) {
			ex.printStackTrace();
			return "false";
		}
	}
}
