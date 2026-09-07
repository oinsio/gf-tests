package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HelloWorld {
    private static final String GREETING = "Hello, World!";
    private static final Path GREETINGS_FILE = Path.of("greetings.txt");
    private static final int HISTORY_DISPLAY_LIMIT = 5;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) throws IOException {
        run(greetingFor(args), System.in, System.out, GREETINGS_FILE, Clock.systemDefaultZone());
    }

    static String greetingFor(String[] args) {
        String name = String.join(" ", args).trim();
        return name.isEmpty() ? GREETING : "Hello, " + name + "!";
    }

    static void run(String greeting, InputStream in, PrintStream out, Path outputFile, Clock clock) throws IOException {
        out.println(greeting);

        List<String> history = readHistory(outputFile);
        out.println("Greeting history:");
        int hiddenCount = history.size() - HISTORY_DISPLAY_LIMIT;
        List<String> displayedHistory = hiddenCount > 0
                ? history.subList(hiddenCount, history.size())
                : history;
        List<String> reversedHistory = new ArrayList<>(displayedHistory);
        Collections.reverse(reversedHistory);
        reversedHistory.forEach(out::println);
        if (hiddenCount > 0) {
            out.println("and " + hiddenCount + " more earlier.");
        }
        out.println("This is greeting number " + (history.size() + 1) + ".");

        out.println("Save greeting to file? (yes/no)");

        Scanner scanner = new Scanner(in);
        String answer = scanner.hasNextLine() ? scanner.nextLine() : "";

        if (isAffirmative(answer)) {
            appendGreeting(greeting, outputFile, clock);
        }
    }

    static List<String> readHistory(Path file) throws IOException {
        return Files.exists(file) ? Files.readAllLines(file) : List.of();
    }

    private static boolean isAffirmative(String answer) {
        String normalized = answer.trim().toLowerCase();
        return normalized.equals("yes") || normalized.equals("y") || normalized.equals("да");
    }

    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    static void appendGreeting(String greeting, Path file, Clock clock) throws IOException {
        String timestamp = LocalDateTime.now(clock).format(TIMESTAMP_FORMAT);
        Files.writeString(file, timestamp + " " + greeting + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
