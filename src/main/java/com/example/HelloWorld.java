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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HelloWorld {
    private static final String GREETING = "Hello, World!";
    private static final Path GREETINGS_FILE = Path.of("greetings.txt");
    private static final int HISTORY_DISPLAY_LIMIT = 5;
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final int TIMESTAMP_LENGTH = 16;

    public static void main(String[] args) throws IOException {
        if (List.of(args).contains("--stats")) {
            GreetingStats.run(System.out, GREETINGS_FILE);
            return;
        }
        run(greetingFor(args), System.in, System.out, GREETINGS_FILE);
    }

    static String greetingFor(String[] args) {
        String name = String.join(" ", args).trim();
        return name.isEmpty() ? GREETING : "Hello, " + name + "!";
    }

    static void run(String greeting, InputStream in, PrintStream out, Path outputFile) throws IOException {
        run(greeting, in, out, outputFile, Clock.systemDefaultZone());
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
        reversedHistory.stream().map(HelloWorld::parseHistoryLine).map(HistoryEntry::display).forEach(out::println);
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

    private record HistoryEntry(LocalDateTime timestamp, String text) {
        String display() {
            return timestamp == null ? text : TIMESTAMP_FORMAT.format(timestamp) + " " + text;
        }
    }

    private static HistoryEntry parseHistoryLine(String line) {
        if (line.length() > TIMESTAMP_LENGTH && line.charAt(TIMESTAMP_LENGTH) == ' ') {
            try {
                LocalDateTime timestamp = LocalDateTime.parse(line.substring(0, TIMESTAMP_LENGTH), TIMESTAMP_FORMAT);
                return new HistoryEntry(timestamp, line.substring(TIMESTAMP_LENGTH + 1));
            } catch (DateTimeParseException e) {
                // not a timestamped line; fall through to treat it as legacy text
            }
        }
        return new HistoryEntry(null, line);
    }

    private static boolean isAffirmative(String answer) {
        String normalized = answer.trim().toLowerCase();
        return normalized.equals("yes") || normalized.equals("y") || normalized.equals("да");
    }

    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    static void appendGreeting(String greeting, Path file) throws IOException {
        appendGreeting(greeting, file, Clock.systemDefaultZone());
    }

    static void appendGreeting(String greeting, Path file, Clock clock) throws IOException {
        String timestamp = TIMESTAMP_FORMAT.format(LocalDateTime.now(clock));
        Files.writeString(file, timestamp + " " + greeting + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
