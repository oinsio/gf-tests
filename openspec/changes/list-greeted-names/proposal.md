## Why

The `--stats` output currently reports totals and the most frequent
greeting, but does not say who was greeted. Users want to see the full,
deduplicated list of names they have greeted alongside the existing
statistics.

## What Changes

- `--stats` output additionally prints the list of distinct names that
  have appeared in saved greetings, with no name repeated even if it was
  greeted multiple times.
- The names list is derived from the same `greetings.txt` history already
  used for the other statistics.

## Capabilities

### New Capabilities
(none)

### Modified Capabilities
- `greeting-stats`: adds a requirement that `--stats` output includes the
  deduplicated list of everyone who was greeted.

## Impact

- `src/main/java/com/example/GreetingStats.java`: extract the name from
  each saved greeting line and print the deduplicated list.
- `src/test/groovy/com/example/GreetingStatsSpec.groovy`: new scenarios
  covering the names list, including deduplication.
