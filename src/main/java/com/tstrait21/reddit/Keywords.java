package com.tstrait21.reddit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Keywords {

    private static final Logger logger = LogManager.getLogger(Keywords.class);

    public List<String> keywordList;

    public Keywords() {
        this.keywordList = getKeywordList();
    }

    private List<String> getKeywordList() {
        List<String> keywordList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(this.getClass().getClassLoader().getResource("keywords.txt").getFile())))) {
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                keywordList.add(currentLine);
            }
        } catch (FileNotFoundException e) {
            logger.error("Error - unable to retrieve keywords.txt.");

            return null;
        } catch (IOException e) {
            logger.error("Error occurred while reading keywords.txt.");

            return null;
        }

        return keywordList;
    }
}
