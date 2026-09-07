## 1. GreetingStats class

- [ ] 1.1 Create `src/main/java/com/example/GreetingStats.java` with a static method that computes total count and most-frequent greeting (with count) from a `Path`, printing `No greetings saved yet.` to a given `PrintStream` when the file is missing or empty. Verify with `src/test/groovy/com/example/GreetingStatsSpec.groovy`: "prints No greetings saved yet when the file does not exist" and "prints No greetings saved yet when the file is empty".
- [ ] 1.2 Implement total-count and most-frequent-greeting output for a non-empty file, breaking ties by first occurrence. Verify with `GreetingStatsSpec`: "prints total count and most frequent greeting with its count" and "breaks ties between equally frequent greetings by first occurrence".

## 2. HelloWorld dispatch on --stats

- [ ] 2.1 In `HelloWorld.main`, detect `--stats` in `args` and delegate to `GreetingStats` instead of computing/printing a greeting, without prompting to save. Verify with `src/test/groovy/com/example/HelloWorldSpec.groovy`: "prints stats and exits when started with --stats, without printing a greeting or prompting to save".

## 3. Full-flow verification

- [ ] 3.1 Run `./gradlew test` and confirm all Spock specs pass, including the new `GreetingStatsSpec` and the updated `HelloWorldSpec` dispatch test.
