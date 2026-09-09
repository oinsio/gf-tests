package com.example

import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class GreetingStatsSpec extends Specification {

    @TempDir
    Path tempDir

    def "prints no greetings message when the file does not exist"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("missing.txt")

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().trim() == "No greetings saved yet."
    }

    def "prints no greetings message when the file exists but is empty"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        Files.createFile(file)

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().trim() == "No greetings saved yet."
    }

    def "reports the total and most frequent greeting for a single saved greeting"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().contains("Total greetings saved: 1")
        output.toString().contains("Most frequent greeting: Hello, World! (1 times)")
    }

    def "reports the total and the clear leader among multiple distinct greetings"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)
        HelloWorld.appendGreeting("Hello, World!", file)

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().contains("Total greetings saved: 4")
        output.toString().contains("Most frequent greeting: Hello, World! (3 times)")
    }

    def "lists the greeted name for a single saved greeting"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().contains("Greeted names: World")
    }

    def "lists a repeated name only once"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().contains("Greeted names: World, Alice")
    }

    def "orders greeted names by first appearance"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, Alice!", file)
        HelloWorld.appendGreeting("Hello, Bob!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().contains("Greeted names: Alice, Bob")
    }

    def "resolves a timestamped greeting to its name"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        Files.writeString(file, "2026-09-07 14:30 Hello, Alice!" + System.lineSeparator())

        when:
        GreetingStats.run(new PrintStream(output), file)

        then:
        output.toString().contains("Greeted names: Alice")
    }
}
