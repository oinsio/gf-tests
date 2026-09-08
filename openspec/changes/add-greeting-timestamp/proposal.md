## Why

`greetings.txt` currently stores only the greeting text, with no record of
when it was saved. Users reviewing the history have no way to tell how old a
saved greeting is, so we need to record a save timestamp with each new entry
while keeping previously saved (untimestamped) lines readable.

## What Changes

- When a greeting is saved, prepend the current local time, formatted as
  `yyyy-MM-dd HH:mm`, followed by a single space, to the greeting before
  appending it to `greetings.txt`.
- The clock used to produce that timestamp SHALL be injectable, so tests can
  supply a fixed clock and assert an exact timestamp value.
- When reading history for display, lines that already have a
  `yyyy-MM-dd HH:mm ` prefix are parsed into their timestamp and greeting
  text; lines without that prefix (the current, pre-change format) are still
  read and displayed unchanged, with no timestamp.
- The printed history continues to show the timestamp (when present) before
  the greeting text, preserving existing ordering, truncation, and
  greeting-number-counting behavior.

## Capabilities

### New Capabilities
- `greeting-timestamp`: defines the timestamp format prepended to saved
  greetings, the injectable clock that produces it, and backward-compatible
  reading/display of both timestamped and legacy untimestamped history lines.

### Modified Capabilities
- `greeting`: the "Greeting drives history and save behavior" requirement
  currently states the bare greeting text is what gets appended to
  `greetings.txt`; this changes so the timestamp-prefixed greeting is what
  gets appended and counted.

## Impact

- `src/main/java/com/example/HelloWorld.java`: `appendGreeting`, `readHistory`,
  and `run` need to produce and parse timestamp-prefixed lines, and `main`
  needs to supply a real clock.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: existing tests that
  assert exact saved/displayed line content need to account for the
  timestamp prefix; new tests need to cover injectable-clock timestamping and
  legacy-format backward compatibility.
