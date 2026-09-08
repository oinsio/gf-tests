## Context

`HelloWorld.appendGreeting(String, Path)` writes the raw greeting line, and
`HelloWorld.readHistory(Path)` reads lines back verbatim for display in
`run(...)`. Neither has any notion of time. See proposal.md for motivation.

## Goals / Non-Goals

**Goals:**
- Make the current-time source a parameter the tests can fix to an exact
  value, rather than a hidden call to `System`/`Clock` internals.
- Keep legacy (untimestamped) lines readable without any file migration.

**Non-Goals:**
- Rewriting or migrating existing lines in `greetings.txt` to add
  timestamps retroactively.
- Configurable timestamp formats or time zones - the format is fixed to
  `yyyy-MM-dd HH:mm` in the system default zone, matching the task.

## Decisions

- **Inject `java.time.Clock` instead of a custom time interface.** `Clock`
  is the standard JDK seam for this exact problem (`Clock.fixed(...)` gives
  Spock an exact, deterministic instant), so no bespoke abstraction is
  needed. Alternative considered: passing a formatted `String` timestamp
  into `appendGreeting` directly - rejected because it pushes formatting
  concerns onto every caller instead of keeping them next to the save
  logic.
- **Detect legacy lines by attempting to parse the leading token as a
  timestamp**, using `DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")` on
  the first 16 characters of a line (plus the following space) and falling
  back to treating the whole line as an untimestamped greeting when parsing
  fails or the line is too short. This needs no delimiter or marker beyond
  the format itself, and a real greeting can never start with 16 characters
  that parse as `yyyy-MM-dd HH:mm` followed by a space.
- **Represent a history entry as a small (timestamp, text) pair internally**
  so `run(...)` can print `timestamp + " " + text` when a timestamp is
  present and just `text` otherwise, keeping the display logic in one place
  rather than re-parsing strings at each print site.

## Risks / Trade-offs

- [A future greeting could theoretically start with 16 characters that
  happen to parse as a valid `yyyy-MM-dd HH:mm` value, causing it to be
  misread as a timestamp] → Accepted: greetings are always `Hello, <name>!`
  or `Hello, World!` in this application, which cannot match that pattern.
- [`Clock` injection changes the signature callers use to save/append a
  greeting] → Mitigation: give the clock parameter a default (system clock)
  overload or default argument at the `main`/`run` entry point so existing
  callers outside the test suite are unaffected.
