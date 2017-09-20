package com.tstrait21.reddit;

import com.tstrait21.reddit.entity.Feed;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RedditDAO dao = new RedditDAO(args[0], args[1]);

        Feed feed = deserializeXml(dao.getListings());

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