package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class HelloWorld {
    private static final Path GREETINGS_FILE = Path.of("greetings.txt");

    public static void main(String[] args) throws IOException {
        System.out.println("Hello, World!");
        appendGreeting("Hello, World!", GREETINGS_FILE);
    }

    public String greet(String name) {
        return "Hello, " + name + "!";
    }

    static void appendGreeting(String greeting, Path file) throws IOException {
        Files.writeString(file, greeting + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
