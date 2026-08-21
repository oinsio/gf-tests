package com.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class HelloWorld {
    private static final String GREETING = "Hello, World!";

    public static void main(String[] args) throws IOException {
        run(System.in, System.out, Path.of("greeting.txt"));
    }

    static void run(InputStream in, PrintStream out, Path outputFile) throws IOException {
        out.println(GREETING);
        out.println("Save greeting to file? (yes/no)");

        Scanner scanner = new Scanner(in);
        String answer = scanner.hasNextLine() ? scanner.nextLine() : "";

        if (isAffirmative(answer)) {
            Files.writeString(outputFile, GREETING + System.lineSeparator());
        }
    }

    private static boolean isAffirmative(String answer) {
        String normalized = answer.trim().toLowerCase();
        return normalized.equals("yes") || normalized.equals("y") || normalized.equals("да");
    }
}
