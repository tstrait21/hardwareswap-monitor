package com.tstrait21.reddit;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;

public class Main {

	private static String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
	// private static String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";

	public static void main(String[] args) {
		System.out.println(retrieveToken());
	}

	private static String retrieveToken() {
		try {
			URL obj = new URL(ACCESS_TOKEN_URL);

			HttpsURLConnection conn = (HttpsURLConnection) obj.openConnection();

			conn.setRequestMethod("POST");

			String userCredentials = "uaUBC9DLV3WVTA:dEYKYUl3NMwaggp5omR8F-tFkzs";
			String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()));
			conn.setRequestProperty("Authorization", basicAuth);

			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			String urlParams = "grant_type=https://oauth.reddit.com/grants/installed_client" + "&redirect_uri=" + "http://localhost:8080";

			conn.setDoOutput(true);
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(urlParams);
			dos.flush();
			dos.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending POST request to URL: " + obj.getPath());
			System.out.println("Response Code: " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();

			return response.toString();
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		}
	}
}
