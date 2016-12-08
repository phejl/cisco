package me.hejl.cisco;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class WordCounter {

    private static final Pattern SEPARATOR_PATTERN = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

    private WordCounter() {
        super();
    }

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.exit(1);
        }

        // the challenge does not specify it should be sorted by frequency
        // though the given sample is sorted so this code sort it as well
        for (Map.Entry<String, Integer> e : sort(count(new File(args[0]), Charset.forName("UTF-8")).entrySet())) {
            System.out.println(e.getValue() + " " + e.getKey());
        }
    }

    public static Map<String, Integer> count(File file, Charset charSet) throws IOException {
        List<String> lines = Files.readAllLines(file.toPath(), charSet);
        Map<String, Integer> result = new HashMap<>();
        for (String line : lines) {
            for (String word : SEPARATOR_PATTERN.split(line)) {
                if (!word.isEmpty()) {
                    result.compute(word, (k, v) -> (v == null) ? 1 : v + 1);
                }
            }
        }
        return result;
    }

    public static Collection<Map.Entry<String, Integer>> sort(Collection<Map.Entry<String, Integer>> counts) {
        List<Map.Entry<String, Integer>> result = new ArrayList<>(counts);
        Collections.sort(result, (o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return result;
    }
}
