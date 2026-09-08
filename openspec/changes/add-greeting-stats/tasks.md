## 1. GreetingStats class

- [ ] 1.1 Create `src/main/java/com/example/GreetingStats.java` with a
      `run(PrintStream out, Path file)` entry point that reads saved
      greetings via `HelloWorld.readHistory(Path)`, and computes the total
      count and the most frequent greeting (ties broken by first
      appearance in the file). Verified by
      `src/test/groovy/com/example/GreetingStatsSpec.groovy`.
- [ ] 1.2 Print `No greetings saved yet.` when there are no saved
      greetings (file missing or empty), otherwise print the total count
      and the most frequent greeting with its count. Verified by
      `src/test/groovy/com/example/GreetingStatsSpec.groovy`.

## 2. HelloWorld dispatch

- [ ] 2.1 In `HelloWorld.main`, detect `--stats` in `args` and delegate to
      `GreetingStats.run(System.out, GREETINGS_FILE)` instead of running
      the normal greet-and-save flow, then return without printing a
      greeting or prompting to save. Verified by
      `src/test/groovy/com/example/HelloWorldSpec.groovy`.

## 3. Spec coverage

- [ ] 3.1 Write `src/test/groovy/com/example/GreetingStatsSpec.groovy`
      covering: no file, empty file, single greeting, multiple distinct
      greetings with a clear most-frequent one. Verified by running
      `./gradlew test` and confirming the new spec passes.
- [ ] 3.2 Add a case to
      `src/test/groovy/com/example/HelloWorldSpec.groovy` asserting that
      launching with `--stats` does not print a greeting or the save
      prompt, and does not modify `greetings.txt`. Verified by running
      `./gradlew test` and confirming the new case passes.
