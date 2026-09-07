## Why

Saved greetings currently have no record of when they were made, so the
history file and the printed history cannot tell the user when a greeting
happened. Adding a save timestamp makes the history meaningful over time
without discarding entries already saved in the old, timestamp-less format.

## What Changes

- When a greeting is saved, prefix it in `greetings.txt` with the save time
  in the format `yyyy-MM-dd HH:mm` followed by a single space, e.g.
  `2026-09-07 14:30 Hello, World!`.
- The clock used to produce this timestamp is injectable, so tests can
  assert an exact, deterministic timestamp instead of depending on the
  system clock.
- When printing the greeting history at startup, each history line that has
  a timestamp prefix is displayed with that timestamp before the greeting
  text.
- History lines already stored without a timestamp (the current format)
  continue to be read and displayed unchanged, without a timestamp prefix.

## Capabilities

### New Capabilities
- `greeting-timestamp`: defines the timestamp format prefixed to a greeting
  when it is saved, the injectable clock used to produce it, and how stored
  history lines are parsed and displayed with or without a timestamp
  depending on their stored format.

### Modified Capabilities
(none — the greeting-history-display capability's truncation, ordering, and
counting behavior is unchanged; it operates on whichever line text
`greeting-timestamp` produces)

## Impact

- `src/main/java/com/example/HelloWorld.java`: saving a greeting, reading
  history, and printing history lines.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: new/updated specs for
  timestamped saving and mixed-format history display.
