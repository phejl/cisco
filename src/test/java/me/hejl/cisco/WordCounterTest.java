package me.hejl.cisco;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WordCounterTest extends TestCase {

    private static final String[] WORDS =
            new String[] {"this", "is", "just", "a", "sample"};

    private static final char[] SEPARATORS =
            new char[] { '\n', ' ', '.', ';'};

    public WordCounterTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(WordCounterTest.class);
    }

    public void testCount() throws IOException {
        File file = File.createTempFile("count_input", "txt");
        try {
            Random random = new Random();
            int[] counts = new int[WORDS.length];
            try (Writer w = new OutputStreamWriter(new FileOutputStream(file), "UTF-8")) {
                for (int i = 0; i < 1000; i++) {
                    int index = random.nextInt(WORDS.length);
                    counts[index]++;
                    w.write(WORDS[index]);
                    w.write(SEPARATORS[random.nextInt(SEPARATORS.length)]);
                }
            }

            Map<String, Integer> result = WordCounter.count(file, Charset.forName("UTF-8"));
            assertEquals(WORDS.length, result.size());
            for (int i = 0; i < WORDS.length; i++) {
                assertEquals(Integer.valueOf(counts[i]), result.get(WORDS[i]));
            }
        } finally {
            file.delete();
        }
    }
}
