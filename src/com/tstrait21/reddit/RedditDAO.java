package com.tstrait21.reddit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;

public class RedditDAO {

    private String access_token;

    private final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
    private final String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";
    private final String DEVICE_ID = "DO_NOT_TRACK_THIS_DEVICE";

    private final String HARDWARESWAP_URL = "https://www.reddit.com/r/hardwareswap/new.xml?sort=new";

    public RedditDAO(String client_id, String secret) {
        this.access_token = retrieveToken(client_id, secret);
    }

    public boolean isTokenValid() {
        if (this.access_token != null) {
            return true;
        } else {
            return false;
        }
    }

    private String retrieveToken(String client_id, String secret) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(ACCESS_TOKEN_URL).openConnection();

            connection = prepareForPost(connection, client_id, secret);

            connection = writeParameters(connection, "grant_type=" + GRANT_TYPE + "&device_id=" + DEVICE_ID);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String inputLine;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();

                String access_token = new Gson().fromJson(response.toString(), JsonObject.class).get("access_token").getAsString();

                return access_token;
            } else {
                System.out.println("Response code was " + responseCode + ".");

                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    private HttpsURLConnection prepareForPost(HttpsURLConnection connection, String client_id, String secret) {
        try {
            connection.setRequestMethod("POST");

            String basicAuth = "Basic " + new String(Base64.getEncoder().encode((client_id + ":" + secret).getBytes()));

            connection.setRequestProperty("Authorization", basicAuth);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            return connection;
        } catch (ProtocolException e) {
            e.printStackTrace();

            return null;
        }
    }

    private HttpsURLConnection writeParameters(HttpsURLConnection connection, String urlParams) {
        try {
            connection.setDoOutput(true);

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

            dos.writeBytes(urlParams);
            dos.flush();
            dos.close();

            return connection;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }

    public String getListings() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(HARDWARESWAP_URL).openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Authorization", this.access_token);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setDoOutput(false);

            int responseCode = connection.getResponseCode();

            if (responseCode == 200) {
                String inputLine;
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();

                return response.toString();
            } else {
                System.out.println("Response code was " + responseCode + ".");

                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }
    }
}
