package com.example

import spock.lang.Specification

class HelloWorldSpec extends Specification {

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
}
