package com.tstrait21.reddit;

import java.io.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class Keywords {

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
            e.printStackTrace();

            return null;
        } catch (IOException e) {
            e.printStackTrace();

            return null;
        }

        return keywordList;
    }
}
