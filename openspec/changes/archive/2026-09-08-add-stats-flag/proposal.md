## Why

Users who have been saving greetings via `greetings.txt` have no way to see
a summary of what has accumulated there without opening the file
themselves. A `--stats` flag lets the application report that summary
directly, without going through the normal greet-and-save flow.

## What Changes

- Add a `--stats` command-line argument. When the application is launched
  with `--stats`, it prints statistics about the saved greetings instead of
  the normal greeting flow, then exits.
- Statistics printed: the total number of saved greetings, and the most
  frequent greeting together with its count.
- If `greetings.txt` does not exist or is empty, print
  `No greetings saved yet.` instead.
- When `--stats` is used, the application does not print a greeting, does
  not print greeting history, and does not prompt to save a greeting.
- The statistics computation is implemented in a new class (e.g.
  `GreetingStats`); `HelloWorld` only dispatches to it based on the
  argument.

## Capabilities

### New Capabilities
- `greeting-stats`: Defines the `--stats` flag's dispatch behavior and the
  statistics (total count, most frequent greeting) it reports based on the
  contents of `greetings.txt`.

### Modified Capabilities
(none — existing greeting/history behavior is unchanged when `--stats` is
not supplied)

## Impact

- `src/main/java/com/example/HelloWorld.java`: add dispatch on `--stats`.
- New `src/main/java/com/example/GreetingStats.java`: computes and formats
  the statistics.
- New Spock spec under `src/test/groovy/com/example/` covering the new
  behavior.
