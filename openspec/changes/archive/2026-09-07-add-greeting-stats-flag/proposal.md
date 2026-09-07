## Why

Users who have been saving greetings have no way to see what has accumulated in `greetings.txt` without opening the file themselves. A `--stats` flag gives a quick summary (total count and most frequent greeting) directly from the CLI.

## What Changes

- Add a `--stats` command-line argument. When present, the application prints greeting statistics and exits immediately: it does not print a greeting, does not print greeting history, and does not prompt to save.
- Statistics printed: total number of saved greetings, and the most frequent greeting together with its count.
- If `greetings.txt` does not exist or is empty, print `No greetings saved yet.` instead.
- Introduce a new `GreetingStats` class responsible for computing and formatting these statistics. `HelloWorld` only dispatches to it when `--stats` is present; it keeps no statistics logic itself.

## Capabilities

### New Capabilities
- `greeting-stats`: computing and printing statistics (total count, most frequent greeting) about saved greetings, including the empty/missing-file case, and triggering this via the `--stats` command-line flag.

### Modified Capabilities
(none — dispatch on `--stats` is new behavior covered by the `greeting-stats` capability above; existing greeting/history/save behavior is unchanged when `--stats` is not passed)

## Impact

- `src/main/java/com/example/HelloWorld.java`: add argument dispatch for `--stats`.
- New file `src/main/java/com/example/GreetingStats.java`: statistics computation and formatting.
- New Spock spec `src/test/groovy/com/example/GreetingStatsSpec.groovy`.
- Possibly extend `src/test/groovy/com/example/HelloWorldSpec.groovy` to cover the dispatch behavior.
