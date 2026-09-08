package com.example;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GreetingStats {

    static void run(PrintStream out, Path file) throws IOException {
        List<String> history = HelloWorld.readHistory(file);

        if (history.isEmpty()) {
            out.println("No greetings saved yet.");
            return;
        }

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String greeting : history) {
            counts.merge(greeting, 1, Integer::sum);
        }

        String mostFrequent = null;
        int mostFrequentCount = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > mostFrequentCount) {
                mostFrequent = entry.getKey();
                mostFrequentCount = entry.getValue();
            }
        }

        out.println("Total greetings saved: " + history.size());
        out.println("Most frequent greeting: " + mostFrequent + " (" + mostFrequentCount + " times)");
    }
}
