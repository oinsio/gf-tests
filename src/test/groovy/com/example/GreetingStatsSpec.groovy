package com.example

import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class GreetingStatsSpec extends Specification {

    @TempDir
    Path tempDir

    def "prints 'No greetings saved yet.' when the file does not exist"() {
        given:
        def file = tempDir.resolve("greetings.txt")
        def output = new ByteArrayOutputStream()

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        output.toString().trim() == "No greetings saved yet."
    }

    def "prints 'No greetings saved yet.' when the file is empty"() {
        given:
        def file = tempDir.resolve("greetings.txt")
        Files.writeString(file, "")
        def output = new ByteArrayOutputStream()

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        output.toString().trim() == "No greetings saved yet."
    }

    def "reports the total count and the clear most-frequent greeting"() {
        given:
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)
        HelloWorld.appendGreeting("Hello, World!", file)
        def output = new ByteArrayOutputStream()

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        output.toString().contains("Total greetings saved: 3")
        output.toString().contains("Hello, World!")
        output.toString().contains("2")
    }

    def "reports one of the greetings as most frequent when all are equally frequent"() {
        given:
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)
        def output = new ByteArrayOutputStream()

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        def text = output.toString()
        text.contains("Total greetings saved: 2")
        (text.contains("Hello, World!") || text.contains("Hello, Alice!"))
        text.contains("1")
    }
}
