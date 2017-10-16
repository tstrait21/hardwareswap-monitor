package com.tstrait21.reddit;

import com.tstrait21.reddit.entity.Feed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    private static final Logger logger = LogManager.getLogger(XmlParser.class);

    private String response;
    public Feed feed;

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
            e.printStackTrace();

            return null;
        }
    }

    public void checkForKeywords(List<String> keywordList) {
        for (int i = 0; i < this.feed.getEntry().size(); i++) {
            logger.info(this.feed.getEntry().get(i).getTitle());
        }

        logger.info("Stuff you may be interested in:");

        for (int i = 0; i < this.feed.getEntry().size(); i++) {
            for(int j = 0; j < keywordList.size(); j++) {
                if (this.feed.getEntry().get(i).getTitle().toLowerCase().contains(keywordList.get(j).toLowerCase())) {
                    logger.info(this.feed.getEntry().get(i).getTitle());
                }
            }
        }
    }
}
