## Why

Users who have been saving greetings to `greetings.txt` have no way to see a
summary of what has accumulated there without opening the file themselves.
Adding a `--stats` flag lets them get an immediate summary from the command
line.

## What Changes

- Add a `--stats` command-line argument. When present, the application prints
  greeting statistics instead of running the normal greet-and-save flow, then
  exits.
- Statistics printed: the total number of saved greetings, and the most
  frequent greeting together with its count.
- If `greetings.txt` does not exist or is empty, print `No greetings saved yet.`
  instead.
- When `--stats` is given, the application does not print a greeting and does
  not prompt to save one.
- Introduce a new `GreetingStats` class responsible for computing and
  formatting these statistics. `HelloWorld` only dispatches to it based on the
  argument.

## Capabilities

### New Capabilities
- `greeting-stats`: computing and printing statistics (total count, most
  frequent greeting) about greetings saved in `greetings.txt`, triggered by
  the `--stats` argument.

### Modified Capabilities
- `greeting`: `HelloWorld` must recognize `--stats` as an argument and
  dispatch to the statistics behaviour instead of the normal greet flow,
  without printing a greeting or prompting to save.

## Impact

- `src/main/java/com/example/HelloWorld.java`: add argument dispatch for
  `--stats`.
- New: `src/main/java/com/example/GreetingStats.java`.
- New Spock spec: `src/test/groovy/com/example/GreetingStatsSpec.groovy`.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: may need updates for
  dispatch coverage.
