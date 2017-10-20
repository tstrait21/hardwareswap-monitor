package com.tstrait21.reddit;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class XmlParserTest {

    @Test
    public void testXmlParserInitialization() {
        try {
            String xml = new String(Files.readAllBytes(Paths.get(new File(this.getClass().getClassLoader().getResource("sample.xml").getFile()).getPath())));

            XmlParser xmlParser = new XmlParser(xml);

            Assert.assertNotNull(xmlParser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckForKeywords() {
        try {
            String xml = new String(Files.readAllBytes(Paths.get(new File(this.getClass().getClassLoader().getResource("sample.xml").getFile()).getPath())));

            XmlParser xmlParser = new XmlParser(xml);

            List<String> keywordList = new ArrayList<>();

            keywordList.add("USA-PA");
            keywordList.add("GTX");
            keywordList.add("Corsair");

            xmlParser.checkForKeywords(keywordList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
