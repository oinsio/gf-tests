## 1. Implementation

- [ ] 1.1 In `GreetingStats.run`, derive the deduplicated, first-seen-order
  list of greeted names from the existing `counts` key set (strip the
  `"Hello, "` prefix and trailing `"!"`) and print it as part of the
  `--stats` output, verified by
  `com.example.GreetingStatsSpec: "Single saved greeting"`.

## 2. Tests

- [ ] 2.1 Add a Spock scenario asserting a name greeted multiple times is
  listed exactly once, verified by
  `com.example.GreetingStatsSpec: "Same name greeted multiple times"`.
- [ ] 2.2 Add a Spock scenario asserting multiple distinct names are all
  listed, each exactly once, verified by
  `com.example.GreetingStatsSpec: "Multiple distinct names"`.
- [ ] 2.3 Run the full test suite (`./gradlew test`) and confirm it passes.
