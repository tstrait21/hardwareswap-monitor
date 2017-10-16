
package com.tstrait21.reddit.entity;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tstrait21.reddit.entity package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tstrait21.reddit.entity
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Feed }
     * 
     */
    public Feed createFeed() {
        return new Feed();
    }

    /**
     * Create an instance of {@link Feed.Entry }
     * 
     */
    public Feed.Entry createFeedEntry() {
        return new Feed.Entry();
    }

    /**
     * Create an instance of {@link Feed.Category }
     * 
     */
    public Feed.Category createFeedCategory() {
        return new Feed.Category();
    }

    /**
     * Create an instance of {@link Feed.Link }
     * 
     */
    public Feed.Link createFeedLink() {
        return new Feed.Link();
    }

    /**
     * Create an instance of {@link Feed.Entry.Author }
     * 
     */
    public Feed.Entry.Author createFeedEntryAuthor() {
        return new Feed.Entry.Author();
    }

    /**
     * Create an instance of {@link Feed.Entry.Category }
     * 
     */
    public Feed.Entry.Category createFeedEntryCategory() {
        return new Feed.Entry.Category();
    }

    /**
     * Create an instance of {@link Feed.Entry.Content }
     * 
     */
    public Feed.Entry.Content createFeedEntryContent() {
        return new Feed.Entry.Content();
    }

    /**
     * Create an instance of {@link Feed.Entry.Link }
     * 
     */
    public Feed.Entry.Link createFeedEntryLink() {
        return new Feed.Entry.Link();
    }

}
