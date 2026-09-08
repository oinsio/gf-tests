## 1. GreetingStats class

- [x] 1.1 Create `src/main/java/com/example/GreetingStats.java` with logic to
      read `greetings.txt`, compute the total count and the most frequent
      greeting with its count, and print either those statistics or
      `No greetings saved yet.` (when the file is missing or empty) to a
      given `PrintStream`. Verify with
      `src/test/groovy/com/example/GreetingStatsSpec.groovy`, covering: a
      missing file, an empty file, multiple greetings with a clear
      most-frequent entry, and greetings that are all equally frequent.

## 2. HelloWorld dispatch

- [x] 2.1 In `HelloWorld.main`, detect the `--stats` argument and delegate to
      `GreetingStats` instead of running the normal greet/history/save flow,
      leaving all other argument handling unchanged. Verify with
      `src/test/groovy/com/example/HelloWorldSpec.groovy`, asserting that
      running with `--stats` prints no greeting and does not prompt to save,
      while running with no arguments or a name argument behaves as before.

## 3. Full verification

- [x] 3.1 Run `./gradlew test` and confirm `GreetingStatsSpec` and
      `HelloWorldSpec` both pass.
