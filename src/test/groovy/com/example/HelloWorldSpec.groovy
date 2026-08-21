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
}
