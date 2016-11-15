package com.nz.java.problems;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Program to produce count of all different words in a text file.
 * Assumptions
 * 1. words are all alphabet characters
 * 2. abbreviated version of words such as 's, 're, Mr. will be treated as ' or . being replaced with an empty char.
 * 3. case sensitive
 * 4. small file size and stored on a single machine
 *
 * Usage: Specify file name as args[0] of the main method
 *
 */
public class WordCount {

    public static void main(String[] args) {
        WordCount wordCount = new WordCount();
        List<String> words = wordCount.readWordsFromFile(args[0]);
        Map<String, Integer> wordToCountMap = wordCount.getWordsCountMap(words);
        // print this map
        for (Map.Entry<String, Integer> entry : wordToCountMap.entrySet()) {
            System.out.println(String.format("%s %s", entry.getValue(), entry.getKey()));
        }
    }

    /**
     * Read all words from a given file
     */
    List<String> readWordsFromFile(String filePath) {
        List<String> allWords = new ArrayList<String>();
        String line;
        try {
            InputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                // replace non-alphabet, non-space with empty char
                line = line.replaceAll("[^a-zA-Z\\s]", "");
                String[] words = line.split(" ");
                allWords.addAll(Arrays.asList(words));
            }
            return allWords;

        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred.", e);
        } catch (IOException ioe) {
            throw new RuntimeException("IOException occurred", ioe);
        }
    }

    /**
     *  Build hash-map to map counts with a given list of words
     */
    Map<String, Integer> getWordsCountMap(List<String> words) {
        Map<String, Integer> wordToCountMap = new HashMap<String, Integer>();
        for (String word : words) {
            // skip empty words
            if (word.length() == 0) {
                continue;
            }
            if (wordToCountMap.containsKey(word)) {
                wordToCountMap.put(word, wordToCountMap.get(word) + 1);
            } else {
                wordToCountMap.put(word, 1);
            }
        }
        return wordToCountMap;
    }
}
