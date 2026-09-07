# Quick start

Instructions for building, running, and checking the results of the `gf-tests` project.

## 1. How to build the project

```bash
./gradlew build
```

The command compiles the source code, builds the Spock test specifications, and runs the tests. The Gradle wrapper (`gradlew`) is used, so there's no need to install Gradle separately — the version is pinned in `gradle/wrapper`.

## 2. How to run the project

```bash
./gradlew run
```

Runs the application via the `application` plugin. The entry point is the `com.example.HelloWorld` class (see `mainClass` in `build.gradle`).

The application is interactive:

1. Prints `Hello, World!` to the console.
2. Prints the history of previous greetings (if the `greetings.txt` file already exists) and the number of the current greeting.
3. Asks `Save greeting to file? (yes/no)` and waits for user input. The answers `yes`, `y`, or `да` (case-insensitive) save the greeting to `greetings.txt`; any other answer (including an empty one) does not.

To answer the question via `./gradlew run`, pass the answer on stdin, for example:

```bash
echo yes | ./gradlew run --console=plain -q
```

## 3. Where to look for the run results

- **Application output** — right in the console after `./gradlew run`.
- **Test results**:
  ```bash
  ./gradlew test
  ```
  - HTML report: `build/reports/tests/test/index.html`
  - XML reports (for CI): `build/test-results/test/`
- **Built artifacts** (classes, jar, etc.) — in the `build/` directory.
- **Greeting history** — the `greetings.txt` file in the project root. It is created/appended to only on a positive answer to the save question (see item 2) and is not created automatically on run.

## 4. What else is worth knowing

- **Java 25** — make sure the appropriate JDK is installed (`sourceCompatibility`/`targetCompatibility` in `build.gradle`).
- **Project structure**:
  ```
  src/main/java/com/example/   — application source code
  src/test/groovy/com/example/ — Spock specifications (tests)
  openspec/                    — specs and workflow configuration (spec-driven development)
  ```
- **Test framework** — Spock 2.4 (Groovy 4.0), run via the JUnit Platform.
- The `build/` directory is not stored in the repository — it is created during the build and can be safely deleted (`./gradlew clean`).
