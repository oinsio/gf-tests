## Why

`--stats` currently reports how many greetings were saved and which one is
most frequent, but it does not say *who* has been greeted. Users want to see
the full set of distinct names they've greeted over time, without opening
`greetings.txt` and picking names out by hand.

## What Changes

- Add a list of every distinct name that has been greeted to the `--stats`
  output, alongside the existing total-count and most-frequent-greeting
  lines.
- Each name appears exactly once in the list, even if it was greeted many
  times.

## Capabilities

### New Capabilities
(none)

### Modified Capabilities
- `greeting-stats`: `--stats` output additionally lists every distinct
  greeted name, deduplicated, when at least one greeting has been saved.

## Impact

- `src/main/java/com/example/GreetingStats.java`: derive names from saved
  greetings and print the deduplicated list.
- `src/test/groovy/com/example/GreetingStatsSpec.groovy`: new coverage for
  the greeted-names list.
