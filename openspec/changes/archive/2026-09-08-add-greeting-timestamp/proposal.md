## Why

Saved greetings in `greetings.txt` currently carry no record of when they
were saved, so the history is just a list of messages with no sense of
when each one happened. Prefixing each saved line with its save time makes
the history meaningful as a log, while still reading files saved before
this change.

## What Changes

- When a greeting is saved, it is appended to `greetings.txt` prefixed with
  the save time in the format `yyyy-MM-dd HH:mm`, followed by a single
  space, e.g. `2026-09-07 14:30 Hello, World!`.
- The startup history display shows the timestamp before each greeting for
  lines that have one.
- Lines already stored in the current format (no timestamp) are still read
  and displayed exactly as they are; no migration or rewrite of existing
  lines is performed.
- The source of the current time is injectable, so it can be fixed to an
  exact value in tests.

## Capabilities

### New Capabilities
- `greeting-timestamp`: defines the timestamp prefix format used when
  saving a greeting, how timestamped and legacy (untimestamped) lines are
  displayed in history, and the requirement that the clock be injectable.

### Modified Capabilities
(none)

## Impact

- `src/main/java/com/example/HelloWorld.java`: the save path
  (`appendGreeting`) and the read/display path (`readHistory`, `run`) need
  to produce and recognize the timestamp prefix; a clock/time source needs
  to become an injectable parameter.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: existing specs that
  assert exact file contents or exact history lines (e.g.
  `appendGreeting creates the file when it does not exist`, `readHistory
  returns the lines already stored in the file`) will need updating for
  the new timestamp prefix, and new specs will exercise the injected clock.
