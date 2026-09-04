package com.example

import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class HelloWorldSpec extends Specification {

    @TempDir
    Path tempDir

    def "should print hello world message"() {
        given:
        def output = new ByteArrayOutputStream()
        System.setOut(new PrintStream(output))

        when:
        HelloWorld.main([] as String[])

        then:
        output.toString().contains("Hello, World!")

        cleanup:
        System.setOut(System.out)
    }

    def "should ask whether to save the greeting to a file"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("Save greeting to file?")
    }

    def "should save the greeting to a file when the user agrees"() {
        given:
        def input = new ByteArrayInputStream("yes\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        Files.exists(outputFile)
        Files.readString(outputFile).contains("Hello, World!")
    }

    def "should not save the greeting to a file when the user declines"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        !Files.exists(outputFile)
    }

    def "addition works correctly"() {
        expect:
        1 + 1 == 2
    }

    def "string operations work"() {
        given:
        def text = "Hello, World!"

        expect:
        text.length() == 13
        text.startsWith("Hello")
        text.endsWith("World!")
        text.contains("World")
    }

    def "greet returns personalized message"() {
        given:
        def helloWorld = new HelloWorld()

        expect:
        helloWorld.greet("Alice") == "Hello, Alice!"
    }

    def "appendGreeting creates the file when it does not exist"() {
        given:
        def file = tempDir.resolve("greetings.txt")

        when:
        HelloWorld.appendGreeting("Hello, World!", file)

        then:
        Files.exists(file)
        Files.readString(file) == "Hello, World!" + System.lineSeparator()
    }

    def "appendGreeting continues writing into the same file"() {
        given:
        def file = tempDir.resolve("greetings.txt")

        when:
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)

        then:
        Files.readString(file) ==
                "Hello, World!" + System.lineSeparator() +
                "Hello, Alice!" + System.lineSeparator()
    }

    def "run prints empty history and greeting number 1 when the file does not exist"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("История приветствий:")
        output.toString().contains("Это приветствие номер 1.")
    }

    def "run prints previous greetings from the history file and the correct greeting number"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        HelloWorld.appendGreeting("Hello, World!", outputFile)
        HelloWorld.appendGreeting("Hello, World!", outputFile)

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        output.toString().count("Hello, World!") == 3
        output.toString().contains("Это приветствие номер 3.")
    }

    def "run prints only the last 5 greetings when history is longer than 5"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..8).each { HelloWorld.appendGreeting("Hello, World! #${it}", outputFile) }

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        def lines = output.toString().readLines()
        !lines.any { it.contains("#1") || it.contains("#2") || it.contains("#3") }
        (4..8).every { n -> lines.any { it.contains("#${n}") } }
        lines.indexOf(lines.find { it.contains("#4") }) < lines.indexOf(lines.find { it.contains("#5") })
    }

    def "run prints и ещё N ранее summary when history exceeds 5 entries"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..8).each { HelloWorld.appendGreeting("Hello, World! #${it}", outputFile) }

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("и ещё 3 ранее")
    }

    def "run does not print и ещё N ранее when history has 5 or fewer entries"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..count).each { HelloWorld.appendGreeting("Hello, World! #${it}", outputFile) }

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        !output.toString().contains("ранее")

        where:
        count << [0, 5]
    }

    def "run reports the correct greeting number when history exceeds 5 entries"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..8).each { HelloWorld.appendGreeting("Hello, World! #${it}", outputFile) }

        when:
        HelloWorld.run(input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("Это приветствие номер 9.")
    }

    def "readHistory returns an empty list when the file does not exist"() {
        given:
        def file = tempDir.resolve("missing.txt")

        expect:
        HelloWorld.readHistory(file) == []
    }

    def "readHistory returns the lines already stored in the file"() {
        given:
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)

        expect:
        HelloWorld.readHistory(file) == ["Hello, World!", "Hello, Alice!"]
    }
}
