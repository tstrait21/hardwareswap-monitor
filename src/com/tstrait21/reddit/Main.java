package com.tstrait21.reddit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

public class Main {

	private static String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";

	private static String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";
	private static String DEVICE_ID = "DO_NOT_TRACK_THIS_DEVICE";

	public static void main(String[] args) {
		System.out.println(retrieveToken(args[0], args[1]));
	}

	private static String retrieveToken(String client_id, String secret) {
		try {
			URL accessTokenUrl = new URL(ACCESS_TOKEN_URL);

			HttpsURLConnection connection = (HttpsURLConnection) accessTokenUrl.openConnection();

			connection.setRequestMethod("POST");

			String userCredentials = client_id + ":" + secret;
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));

			connection.setRequestProperty("Authorization", basicAuth);
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			String urlParams = "grant_type=" + GRANT_TYPE
					+ "&device_id=" + DEVICE_ID;

			connection.setDoOutput(true);
			DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
			dos.writeBytes(urlParams);
			dos.flush();
			dos.close();

			int responseCode = connection.getResponseCode();

			System.out.println("Response code : " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

			System.out.println(response.toString());

			JsonObject jsonObj = new Gson().fromJson(response.toString(), JsonObject.class);

			String access_token = jsonObj.get("access_token").getAsString();

			return access_token;
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		}
	}
}
