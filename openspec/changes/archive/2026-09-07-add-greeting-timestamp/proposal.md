## Why

Saved greetings currently have no record of when they were made, so the
history in `greetings.txt` can't tell a user when a past greeting happened.
Stamping each saved line with its save time makes the history meaningful
without breaking the entries already on disk.

## What Changes

- When a greeting is saved, prepend the save time to the line written to
  `greetings.txt`, formatted `yyyy-MM-dd HH:mm` followed by a single space,
  e.g. `2026-09-07 14:30 Hello, World!`.
- When printing the startup history, show the timestamp before each
  greeting that has one.
- Lines already stored in the old, timestamp-less format continue to be
  read and displayed exactly as they are (as plain greeting text, with no
  timestamp shown for them).
- The source of the current time used for the timestamp is injectable, so
  tests can supply a fixed clock and assert an exact timestamp.

## Capabilities

### New Capabilities
(none)

### Modified Capabilities
- `greeting`: saving a greeting now writes a `yyyy-MM-dd HH:mm `-prefixed
  line instead of the bare greeting text; the time source is injectable.
- `greeting-history-display`: displayed history lines show the timestamp
  prefix when the stored line has one, and are displayed unchanged when it
  doesn't.

## Impact

- `src/main/java/com/example/HelloWorld.java`: greeting save/read/display
  logic, plus a way to supply the current time to `run`/`appendGreeting`.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: new/updated specs
  covering the timestamp prefix on save and on display, and mixed-format
  history (old lines without a timestamp alongside new lines with one).
- `greetings.txt`: no migration; existing lines stay as-is.
