## 1. Greeted-names list in GreetingStats

- [ ] 1.1 In `GreetingStats.run`, for each saved greeting line, extract the
      name (the text between `Hello, ` and the trailing `!`), and collect
      the unique names in first-occurrence order (e.g. via
      `LinkedHashSet<String>`). Verified by
      `src/test/groovy/com/example/GreetingStatsSpec.groovy`.
- [ ] 1.2 Print `Greeted: ` followed by the collected names joined with
      `, `, after the existing total-count and most-frequent-greeting
      lines, whenever statistics are printed (i.e. `greetings.txt` exists
      and is non-empty). Verified by
      `src/test/groovy/com/example/GreetingStatsSpec.groovy`.

## 2. Spec coverage

- [ ] 2.1 Add cases to
      `src/test/groovy/com/example/GreetingStatsSpec.groovy` covering: a
      single saved greeting (`Greeted: World`), the same name saved
      multiple times (listed once), and multiple distinct names kept in
      first-occurrence order. Verified by running `./gradlew test` and
      confirming the new cases pass.
