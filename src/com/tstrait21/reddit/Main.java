package com.tstrait21.reddit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        RedditDAO dao = new RedditDAO(args[0], args[1]);

        if (dao.isTokenValid()) {
            XmlParser xmlParser = new XmlParser(dao.getListings());

            Keywords keywords = new Keywords("/resources/keywords.txt");

            xmlParser.checkForKeywords(keywords.keywordList);
        } else {
            logger.fatal("Error - issue retrieving access token.");
        }
    }
}