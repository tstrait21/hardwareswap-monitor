package com.tstrait21.reddit;

public class Main {

    public static void main(String[] args) {
        RedditDAO dao = new RedditDAO(args[0], args[1]);

        if (dao.isTokenValid()) {
            XmlParser xmlParser = new XmlParser(dao.getListings());

            xmlParser.checkForKeywords();
        } else {
            System.out.println("Error - issue retrieving access token.");
        }
    }
}