## 1. Name extraction helper

- [ ] 1.1 Add a package-private `HelloWorld` helper that, given a raw
      `greetings.txt` line, strips any timestamp prefix and returns the bare
      name (stripping the `Hello, ` / `!` wrapper, e.g. `World` or `Alice`),
      reusing the existing timestamp-detection logic rather than
      duplicating it. Verified by
      `src/test/groovy/com/example/GreetingStatsSpec.groovy` (task 2.1-2.4
      below exercise it end to end).

## 2. Greeted-names list in `--stats` output

- [ ] 2.1 In `GreetingStats.run`, collect distinct names (via a
      `LinkedHashSet<String>` built in the existing counting loop) and print
      `Greeted names: <name1>, <name2>, ...` after the existing total and
      most-frequent lines, whenever at least one greeting is saved. Verified
      by `GreetingStatsSpec`: `"lists the greeted name for a single saved
      greeting"` asserting the output contains `Greeted names: World`.
- [ ] 2.2 Verify repeated greetings to the same name collapse to one entry.
      Verified by `GreetingStatsSpec`: `"lists a repeated name only once"`
      using `Hello, World!` saved three times and `Hello, Alice!` once,
      asserting the output contains `Greeted names: World, Alice`.
- [ ] 2.3 Verify distinct names are ordered by first appearance in
      `greetings.txt`. Verified by `GreetingStatsSpec`: `"orders greeted
      names by first appearance"` using `Hello, Alice!`, `Hello, Bob!`,
      `Hello, Alice!` in that order, asserting the output contains
      `Greeted names: Alice, Bob`.
- [ ] 2.4 Verify a timestamped line still resolves to its bare name.
      Verified by `GreetingStatsSpec`: `"resolves a timestamped greeting to
      its name"` using the saved line `2026-09-07 14:30 Hello, Alice!`,
      asserting the output contains `Greeted names: Alice`.
