## Context

`HelloWorld.appendGreeting(greeting, file)` writes a single line to
`greetings.txt`, and `HelloWorld.readHistory(file)` returns raw lines that
`run` prints back out (see proposal.md - Why). Neither method currently has
any notion of time, and `readHistory` treats every line as opaque greeting
text.

## Goals / Non-Goals

**Goals:**
- Prefix saved lines with a `yyyy-MM-dd HH:mm` timestamp from an injectable
  clock.
- Parse a stored line into an optional timestamp and the greeting text, so
  both timestamped and legacy lines print correctly.

**Non-Goals:**
- Changing the truncation, ordering, or counting behavior of
  `greeting-history-display` — it keeps operating on whichever text
  `greeting-timestamp` produces for each line.
- Storing timestamps with second/millisecond precision or timezone info.
- Migrating existing untimestamped lines in `greetings.txt` to add
  timestamps.

## Decisions

- **Clock injection via `java.time.Clock`**: add a `Clock` parameter to
  `appendGreeting` (and thread it through `run`), defaulting to
  `Clock.systemDefaultZone()` in `main`. `Clock` is the standard JDK seam
  for testable time and pairs directly with
  `LocalDateTime.now(clock)` and a `DateTimeFormatter` for
  `yyyy-MM-dd HH:mm`, avoiding a hand-rolled clock abstraction.
- **Prefix format is a fixed-width regex/pattern match, not a delimiter
  search**: a stored line is recognized as timestamped when it starts with
  `yyyy-MM-dd HH:mm ` (16 characters + one space) matching
  `DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")`; otherwise the whole
  line is treated as untimestamped greeting text. This is unambiguous
  because greeting text never begins with that exact pattern in practice,
  and avoids needing a separate stored schema/version marker.
- **Parsing happens at print time, not at append time**: `readHistory`
  keeps returning raw lines (used as-is by tests and by counting logic in
  `greeting-history-display`); a small helper splits a line into
  timestamp + text only where display formatting is needed. This keeps the
  line count and history-truncation logic in `run` unchanged.

## Risks / Trade-offs

- [A future greeting whose text happens to start with a valid
  `yyyy-MM-dd HH:mm ` pattern would be misread as having a timestamp] →
  Acceptable: greetings are generated from `Hello, <name>!`, which cannot
  produce this pattern, and the risk is purely cosmetic (display only).
- [`Clock.systemDefaultZone()` ties saved timestamps to the machine's local
  timezone] → Acceptable per the task's format requirement
  (`yyyy-MM-dd HH:mm`, no timezone); consistent with the existing
  single-machine, single-user scope of the app.
