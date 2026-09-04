# Gnomish Factory tests

Sandbox project for testing Java/Gradle/Spock setup for https://github.com/oinsio/gnomish-factory.

## Technologies

- Java 25
- Gradle 9.7
- Spock Framework 2.4 + Groovy 4.0
- OpenSpec (spec-driven development)

## Run application

```bash
./gradlew run
```

Prints `Hello, World!`.

## Features

- Prints the `Hello, World!` greeting.
- Shows the history of previously saved greetings on each run.
- Reports the sequence number of the current greeting (e.g. "Это приветствие номер 3.").
- Prompts the user whether to save the greeting, accepting `yes`/`y`/`да` as confirmation.
- Appends confirmed greetings to `greetings.txt`, preserving prior entries.
- `HelloWorld#greet(name)` builds a personalized greeting message.

## Run tests

```bash
./gradlew test
```

## Project structure

```
src/
  main/java/com/example/   — source code
  test/groovy/com/example/ — Spock specs
openspec/                  — specs and workflow configuration
```
