package com.tstrait21.reddit;

import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlParserTest {

    @Test
    public void testCheckForListings() {
        try {
            String xml = new String(Files.readAllBytes(Paths.get(new File(this.getClass().getClassLoader().getResource("sample.xml").getFile()).getPath())));

            XmlParser xmlParser = new XmlParser(xml);

            Assert.assertNotNull(xmlParser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
