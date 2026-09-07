package com.example;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GreetingStats {

    static void print(Path file, PrintStream out) throws IOException {
        List<String> history = HelloWorld.readHistory(file);

        if (history.isEmpty()) {
            out.println("No greetings saved yet.");
            return;
        }

        Map<String, Long> counts = new LinkedHashMap<>();
        for (String greeting : history) {
            counts.merge(greeting, 1L, Long::sum);
        }

        String mostFrequent = null;
        long mostFrequentCount = 0;
        for (Map.Entry<String, Long> entry : counts.entrySet()) {
            if (entry.getValue() > mostFrequentCount) {
                mostFrequent = entry.getKey();
                mostFrequentCount = entry.getValue();
            }
        }

        out.println("Total greetings saved: " + history.size());
        out.println("Most frequent greeting: " + mostFrequent + " (" + mostFrequentCount + " times)");
    }
}
