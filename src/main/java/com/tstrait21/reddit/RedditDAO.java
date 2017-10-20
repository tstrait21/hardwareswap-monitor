package com.tstrait21.reddit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RedditDAO {

    private static final Logger logger = LogManager.getLogger(RedditDAO.class);

    private final String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";
    private final String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";
    private final String DEVICE_ID = "DO_NOT_TRACK_THIS_DEVICE";

    private final String HARDWARESWAP_URL = "https://www.reddit.com/r/hardwareswap/new.xml?sort=new";

    private String access_token;

    public RedditDAO(String client_id, String secret) {
        this.access_token = retrieveToken(client_id, secret);
    }

    private String retrieveToken(String client_id, String secret) {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(ACCESS_TOKEN_URL).openConnection();

            connection = prepareRequest(connection, "POST", "Basic " + new String(Base64.getEncoder().encode((client_id + ":" + secret).getBytes())));

            connection = writeParameters(connection, "grant_type=" + GRANT_TYPE + "&device_id=" + DEVICE_ID);

            try {
                return new Gson().fromJson(receiveResponse(connection), JsonObject.class).get("access_token").getAsString();
            } catch (NullPointerException e) {
                logger.error("Error retrieving access token.  Invalid credentials.");

                return null;
            }
        } catch (IOException e) {
            logger.error("Error - unable to establish connection when retrieving access token.");

            return null;
        }
    }

    private HttpsURLConnection prepareRequest(HttpsURLConnection connection, String requestType, String credentials) throws ProtocolException {
        connection.setRequestMethod(requestType);

        connection.setRequestProperty("Authorization", credentials);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        return connection;
    }

    private HttpsURLConnection writeParameters(HttpsURLConnection connection, String urlParams) throws IOException {
        connection.setDoOutput(true);

        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

        dos.writeBytes(urlParams);
        dos.flush();
        dos.close();

        return connection;
    }

    private String receiveResponse(HttpsURLConnection connection) throws IOException {
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
            logger.warn("Response code was " + responseCode + ".");

            return null;
        }
    }

    public boolean isTokenValid() {
        if (this.access_token != null) {
            return true;
        } else {
            return false;
        }
    }

    public String getListings() {
        try {
            HttpsURLConnection connection = (HttpsURLConnection) new URL(HARDWARESWAP_URL).openConnection();

            connection = prepareRequest(connection, "GET", this.access_token);

            connection.setDoOutput(false);

            return receiveResponse(connection);
        } catch (IOException e) {
            logger.error("Error - unable to retrieve listings.");

            return null;
        }
    }
}
