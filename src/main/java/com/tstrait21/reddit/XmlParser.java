package com.tstrait21.reddit;

import com.tstrait21.reddit.entity.Feed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import java.io.IOError;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    private static final Logger logger = LogManager.getLogger(XmlParser.class);

    private String response;
    private Feed feed;

    public XmlParser(String response) {
        this.response = response;
        this.feed = deserializeXml();
    }

    private Feed deserializeXml() {
        logger.debug(this.response);

        try {
            JAXBContext context = JAXBContext.newInstance(Feed.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            StringReader reader = new StringReader(this.response);

            Feed feed = (Feed) unmarshaller.unmarshal(reader);

            return feed;
        } catch (JAXBException e) {
            logger.error("Error - issue consuming the response from Reddit's API.");

            return null;
        } catch (NullPointerException e) {
            logger.error("Error - issue reading the response from Reddit's API.");

            return null;
        }
    }

    public boolean isFeedValid() {
        if (this.feed != null) {
            return true;
        } else {
            return false;
        }
    }

    public void checkForKeywords(List<String> keywordList) {
        logger.info("The 25 most recent posts:");

        for (int i = 0; i < this.feed.getEntry().size(); i++) {
            logger.info("\t" + this.feed.getEntry().get(i).getTitle());
        }

        logger.info("Stuff that may interest you from the 25 most recent posts:");

        for (int i = 0; i < this.feed.getEntry().size(); i++) {
            for(int j = 0; j < keywordList.size(); j++) {
                if (this.feed.getEntry().get(i).getTitle().toLowerCase().contains(keywordList.get(j).toLowerCase())) {
                    logger.info("\t" + this.feed.getEntry().get(i).getTitle());
                }
            }
        }
    }
}
