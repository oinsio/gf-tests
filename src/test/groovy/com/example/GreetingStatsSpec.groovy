package com.example

import spock.lang.Specification
import spock.lang.TempDir

import java.nio.file.Files
import java.nio.file.Path

class GreetingStatsSpec extends Specification {

    @TempDir
    Path tempDir

    def "prints No greetings saved yet when the file does not exist"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        output.toString().contains("No greetings saved yet.")
    }

    def "prints No greetings saved yet when the file is empty"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        Files.writeString(file, "")

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        output.toString().contains("No greetings saved yet.")
    }

    def "prints total count and most frequent greeting with its count"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        output.toString().contains("4")
        output.toString().contains("Hello, World!")
        output.toString().contains("3")
    }

    def "breaks ties between equally frequent greetings by first occurrence"() {
        given:
        def output = new ByteArrayOutputStream()
        def file = tempDir.resolve("greetings.txt")
        HelloWorld.appendGreeting("Hello, World!", file)
        HelloWorld.appendGreeting("Hello, Alice!", file)

        when:
        GreetingStats.print(file, new PrintStream(output))

        then:
        output.toString().contains("Hello, World!")
        !output.toString().contains("Hello, Alice!")
    }
}
