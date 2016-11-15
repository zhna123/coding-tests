package com.nz.java.problems;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test for {@link WordCount}
 */
public class WordCountTest {

    @Test
    public void testWordCount() {
        WordCount wordCount = new WordCount();
        List<String> words = wordCount.readWordsFromFile("src/test/resources/words_file.txt");
        Map<String, Integer> actualMap = wordCount.getWordsCountMap(words);
        Map<String, Integer> expectedMap = new HashMap<String, Integer>();
        expectedMap.put("Mr", 1);
        expectedMap.put("Mrs", 2);
        expectedMap.put("youre", 1);
        expectedMap.put("very", 3);
        expectedMap.put("good", 3);
        expectedMap.put("You", 1);
        expectedMap.put("hello", 3);
        expectedMap.put("Good", 1);
        expectedMap.put("Hello", 1);
        Assert.assertEquals(expectedMap.size(), actualMap.size());
        Assert.assertEquals(expectedMap.entrySet(), actualMap.entrySet());
    }
}
