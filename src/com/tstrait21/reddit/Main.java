package com.tstrait21.reddit;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tstrait21.reddit.entity.Feed;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Main {

    private static String ACCESS_TOKEN_URL = "https://www.reddit.com/api/v1/access_token";

    private static String HARDWARESWAP_URL = "https://www.reddit.com/r/hardwareswap/new.xml?sort=new";

    private static String GRANT_TYPE = "https://oauth.reddit.com/grants/installed_client";
    private static String DEVICE_ID = "DO_NOT_TRACK_THIS_DEVICE";

    public static void main(String[] args) {
        String access_token = (retrieveToken(args[0], args[1]));

        Feed feed = deserializeXml(getListings(access_token));

        for (int i = 0; i < feed.getEntry().size(); i++) {
            System.out.println(feed.getEntry().get(i).getTitle());
        }

        List<String> keywords = new ArrayList<>();

        keywords = setKeywords(keywords);

        System.out.println("Stuff you may be interested in:");

        for (int i = 0; i < feed.getEntry().size(); i++) {
            for(int j = 0; j < keywords.size(); j++) {
                if (feed.getEntry().get(i).getTitle().contains(keywords.get(j))) {
                    System.out.println(feed.getEntry().get(i).getTitle());
                }
            }
        }
    }

    private static String retrieveToken(String client_id, String secret) {
        try {
            URL accessTokenUrl = new URL(ACCESS_TOKEN_URL);

            HttpsURLConnection connection = (HttpsURLConnection) accessTokenUrl.openConnection();

            connection.setRequestMethod("POST");

            String userCredentials = client_id + ":" + secret;
            System.out.println(userCredentials);
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

    private static String getListings(String access_token) {
        try {
            URL hardwareswapUrl = new URL(HARDWARESWAP_URL);

            HttpsURLConnection connection = (HttpsURLConnection) hardwareswapUrl.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("Authorization", access_token);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setDoOutput(false);

            int responseCode = connection.getResponseCode();

            System.out.println("Response code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static Feed deserializeXml(String response) {
        System.out.println(response);

        try {
            JAXBContext context = JAXBContext.newInstance(Feed.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(response);

            Feed feed = (Feed) unmarshaller.unmarshal(reader);

            return feed;
        } catch (JAXBException e) {
            e.printStackTrace();

            return null;
        }
    }

    private static List<String> setKeywords(List<String> keywords) {
        keywords.add("USA-PA");
        keywords.add("FE");
        keywords.add("Founders Edition");

        return keywords;
    }
}