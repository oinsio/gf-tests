## Why

`--stats` currently reports the total count and the single most frequent
greeting, but it never shows *who* has been greeted. Users who have saved
many greetings have no quick way to see the full set of distinct people
they've greeted without opening `greetings.txt` and reading through every
line themselves.

## What Changes

- `--stats` output additionally prints the list of unique names that have
  been greeted, derived from the name portion of each saved greeting (the
  text between `Hello, ` and the trailing `!`).
- Each name appears once in this list even if it was greeted multiple
  times, ordered by when it was first greeted.
- This list is printed only when statistics are printed at all (i.e. not
  when `greetings.txt` is missing or empty, which still prints
  `No greetings saved yet.` per the existing requirement).

## Capabilities

### Modified Capabilities
- `greeting-stats`: `--stats` output gains a line listing every unique
  greeted name, in addition to the existing total count and most frequent
  greeting.

## Impact

- `src/main/java/com/example/GreetingStats.java`: compute and print the
  deduplicated list of greeted names.
- `src/test/groovy/com/example/GreetingStatsSpec.groovy`: cover the new
  output line, including deduplication.
