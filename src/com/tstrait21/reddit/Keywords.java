package com.tstrait21.reddit;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Keywords {

    private String filepath;
    public List<String> keywordList;

    public Keywords(String filepath) {
        this.filepath = filepath;
        this.keywordList = getKeywordList();
    }

    private List<String> getKeywordList() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.filepath))) {
            String currentLine;

            while ((currentLine = br.readLine()) != null) {
                this.keywordList.add(currentLine);
            }
        } catch (IOException e) {

        }

        return new ArrayList<>();
    }
}
