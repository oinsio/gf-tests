package com.example;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GreetingStats {

    static void print(Path file, PrintStream out) throws IOException {
        List<String> greetings = Files.exists(file) ? Files.readAllLines(file) : List.of();

        if (greetings.isEmpty()) {
            out.println("No greetings saved yet.");
            return;
        }

        Map<String, Long> counts = greetings.stream()
                .collect(Collectors.groupingBy(g -> g, Collectors.counting()));

        Map.Entry<String, Long> mostFrequent = counts.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow();

        out.println("Total greetings saved: " + greetings.size());
        out.println("Most frequent greeting: " + mostFrequent.getKey()
                + " (" + mostFrequent.getValue() + " times)");
    }
}
