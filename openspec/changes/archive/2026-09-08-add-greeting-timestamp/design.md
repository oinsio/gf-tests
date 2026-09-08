## Context

`HelloWorld.appendGreeting(String, Path)` writes the bare greeting line, and
`HelloWorld.readHistory(Path)` returns raw lines that `run` prints as-is. Both
are static methods with no clock dependency. See proposal.md for motivation.

## Goals / Non-Goals

**Goals:**
- Make the save timestamp's clock injectable without changing `main`'s
  zero-argument CLI behavior.
- Keep `greetings.txt` lines human-readable and keep old files loading
  correctly.

**Non-Goals:**
- No change to the on-disk file format beyond adding the leading timestamp
  (still one greeting per line, plain text).
- No timezone configuration; the injected clock's zone is used as-is
  (`java.time.Clock` already carries a zone).

## Decisions

- Use `java.time.Clock` as the injectable clock type, since it's the
  standard JDK abstraction for exactly this purpose (`Clock.systemDefaultZone()`
  in production, `Clock.fixed(...)` in tests) and needs no extra dependency.
- Add a `Clock` parameter to `appendGreeting` (e.g.
  `appendGreeting(String greeting, Path file, Clock clock)`) and format the
  timestamp with
  `DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")` applied to
  `LocalDateTime.now(clock)`. `run` gains a matching `Clock` parameter it
  passes through to `appendGreeting`; `main` passes
  `Clock.systemDefaultZone()`. Alternative considered: read `System.currentTimeMillis()`
  directly — rejected because it isn't injectable/mockable without a wrapper,
  which is exactly what `Clock` already is.
- Represent a parsed history line as a small `HistoryEntry` (or equivalent
  record: timestamp text or null, greeting text) produced by a new parsing
  helper, e.g. `parseHistoryLine(String)`. It matches lines against
  `^\d{4}-\d{2}-\d{2} \d{2}:\d{2} (.*)$`; on match it splits timestamp and
  greeting, otherwise the whole line is the greeting with no timestamp.
  Alternative considered: try `LocalDateTime.parse` on the first 16 chars
  and catch `DateTimeParseException` — rejected in favor of a regex check
  since it avoids relying on exceptions for control flow and makes the
  legacy-format fallback explicit.
- `run` builds each displayed line by reconstructing `timestamp + " " +
  greeting` when a timestamp is present, or just `greeting` otherwise,
  preserving current ordering/truncation/counting logic untouched (it
  operates on parsed entries, not raw strings, but produces the same shape
  of output).

## Risks / Trade-offs

- [Ambiguous legacy line that happens to start with a date-like pattern but
  isn't actually a timestamp] → Acceptable: the task only requires bare
  compatibility with the current untimestamped format; a false-positive
  match is not a realistic case for hand-entered greeting text and is out
  of scope to guard against.
- [Clock's zone affecting the recorded timestamp] → Mitigation: production
  uses `Clock.systemDefaultZone()`, matching current user-visible local-time
  expectations; tests use `Clock.fixed` with an explicit zone for
  determinism.
