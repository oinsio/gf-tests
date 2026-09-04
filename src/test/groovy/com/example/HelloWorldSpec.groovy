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
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("Save greeting to file?")
    }

    def "should save the greeting to a file when the user agrees"() {
        given:
        def input = new ByteArrayInputStream("yes\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

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
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

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
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

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
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        output.toString().count("Hello, World!") == 3
        output.toString().contains("Это приветствие номер 3.")
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

    def "greetingFor returns the default greeting with no arguments"() {
        expect:
        HelloWorld.greetingFor([] as String[]) == "Hello, World!"
    }

    def "greetingFor returns a personalized greeting for a single argument"() {
        expect:
        HelloWorld.greetingFor(["Alice"] as String[]) == "Hello, Alice!"
    }

    def "greetingFor trims surrounding whitespace from a single argument"() {
        expect:
        HelloWorld.greetingFor(["  Alice  "] as String[]) == "Hello, Alice!"
    }

    def "greetingFor joins multiple arguments with a space"() {
        expect:
        HelloWorld.greetingFor(["Alice", "Smith"] as String[]) == "Hello, Alice Smith!"
    }

    def "greetingFor falls back to the default greeting for a single blank argument"() {
        expect:
        HelloWorld.greetingFor([""] as String[]) == "Hello, World!"
    }

    def "greetingFor falls back to the default greeting for a single whitespace-only argument"() {
        expect:
        HelloWorld.greetingFor(["   "] as String[]) == "Hello, World!"
    }

    def "personalized greeting is appended to the file when the user agrees"() {
        given:
        def input = new ByteArrayInputStream("yes\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")

        when:
        HelloWorld.run(HelloWorld.greetingFor(["Alice"] as String[]), input, new PrintStream(output), outputFile)

        then:
        Files.exists(outputFile)
        Files.readString(outputFile).contains("Hello, Alice!")
    }

    def "personalized greeting counts correctly in the printed history"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        HelloWorld.appendGreeting("Hello, World!", outputFile)

        when:
        HelloWorld.run(HelloWorld.greetingFor(["Alice"] as String[]), input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("Hello, Alice!")
        output.toString().contains("Это приветствие номер 2.")
    }

    def "should print history newest first when history exceeds the display limit"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..8).each { HelloWorld.appendGreeting("Hello, Number ${it}!", outputFile) }

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        def lines = output.toString().readLines()
        def displayed = lines.findAll { it.startsWith("Hello, Number") }
        displayed == ["Hello, Number 8!", "Hello, Number 7!", "Hello, Number 6!",
                       "Hello, Number 5!", "Hello, Number 4!"]
        lines.indexOf("и ещё 3 ранее.") > lines.indexOf("Hello, Number 4!")
    }

    def "run prints all history entries newest first when there are 5 or fewer"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..5).each { HelloWorld.appendGreeting("Hello, Number ${it}!", outputFile) }

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        def lines = output.toString().readLines()
        def displayed = lines.findAll { it.startsWith("Hello, Number") }
        displayed == ["Hello, Number 5!", "Hello, Number 4!", "Hello, Number 3!",
                       "Hello, Number 2!", "Hello, Number 1!"]
    }

    def "run prints the hidden-count summary line when history exceeds 5 entries"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..8).each { HelloWorld.appendGreeting("Hello, Number ${it}!", outputFile) }

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("и ещё 3 ранее.")
    }

    def "run omits the hidden-count summary line when history has 5 or fewer entries"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..5).each { HelloWorld.appendGreeting("Hello, Number ${it}!", outputFile) }

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        !output.toString().contains("ранее.")
    }

    def "run omits the hidden-count summary line when history is empty"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        !output.toString().contains("ранее.")
    }

    def "run reports the correct greeting number when history is truncated"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..8).each { HelloWorld.appendGreeting("Hello, Number ${it}!", outputFile) }

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        output.toString().contains("Это приветствие номер 9.")
    }

    def "should still report the total greeting count after reversing the display order"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        (1..8).each { HelloWorld.appendGreeting("Hello, Number ${it}!", outputFile) }

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        def lines = output.toString().readLines()
        def displayed = lines.findAll { it.startsWith("Hello, Number") }
        displayed.size() == 5
        output.toString().contains("Это приветствие номер 9.")
    }

    def "run prints history newest first when there are fewer than 5 entries"() {
        given:
        def input = new ByteArrayInputStream("no\n".bytes)
        def output = new ByteArrayOutputStream()
        def outputFile = tempDir.resolve("greeting.txt")
        HelloWorld.appendGreeting("Hello, Number 1!", outputFile)
        HelloWorld.appendGreeting("Hello, Number 2!", outputFile)

        when:
        HelloWorld.run("Hello, World!", input, new PrintStream(output), outputFile)

        then:
        def lines = output.toString().readLines()
        def displayed = lines.findAll { it.startsWith("Hello, Number") }
        displayed == ["Hello, Number 2!", "Hello, Number 1!"]
        !output.toString().contains("ранее.")
    }

    def "main prints a personalized greeting when a name argument is supplied"() {
        given:
        def output = new ByteArrayOutputStream()
        System.setOut(new PrintStream(output))
        System.setIn(new ByteArrayInputStream(new byte[0]))

        when:
        HelloWorld.main(["Alice"] as String[])

        then:
        output.toString().contains("Hello, Alice!")

        cleanup:
        System.setOut(System.out)
        System.setIn(System.in)
    }
}
