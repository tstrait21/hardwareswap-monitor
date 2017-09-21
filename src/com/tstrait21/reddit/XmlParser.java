package com.tstrait21.reddit;

import com.tstrait21.reddit.entity.Feed;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {

    private String response;
    private Feed feed;

    public XmlParser(String response) {
        this.response = response;
        this.feed = deserializeXml();
    }

    private Feed deserializeXml() {
        System.out.println(this.response);

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

    public List<String> setKeywords(List<String> keywords) {
        keywords.add("USA-PA");
        keywords.add("FE");
        keywords.add("Founders Edition");

        return keywords;
    }

    public void checkForKeywords() {
        for (int i = 0; i < this.feed.getEntry().size(); i++) {
            System.out.println(this.feed.getEntry().get(i).getTitle());
        }

        List<String> keywords = new ArrayList<>();

        keywords = this.setKeywords(keywords);

        System.out.println("Stuff you may be interested in:");

        for (int i = 0; i < this.feed.getEntry().size(); i++) {
            for(int j = 0; j < keywords.size(); j++) {
                if (this.feed.getEntry().get(i).getTitle().contains(keywords.get(j))) {
                    System.out.println(this.feed.getEntry().get(i).getTitle());
                }
            }
        }
    }
}
