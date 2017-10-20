package com.tstrait21.reddit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            RedditDAO dao = new RedditDAO(args[0], args[1]);

            if (dao.isTokenValid()) {
                XmlParser xmlParser = new XmlParser(dao.getListings());

                if (xmlParser.isFeedValid()) {
                    xmlParser.checkForKeywords(new Keywords().keywordList);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.fatal("Error - no credentials provided.  Please provide client_id and client_secret as parameters.");
        }
    }
}