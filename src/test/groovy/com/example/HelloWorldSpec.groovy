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
}
