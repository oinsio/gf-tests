## Context

`HelloWorld.appendGreeting` currently writes the bare greeting line, and
`HelloWorld.run` reads every stored line as plain greeting text with no
structure. Both need to change to handle a `yyyy-MM-dd HH:mm ` prefix while
`readHistory`/`run` must keep accepting lines that have no such prefix. See
proposal.md for the motivation.

## Goals / Non-Goals

**Goals:**
- Prefix newly saved lines with the save timestamp.
- Display the timestamp for lines that have one, and display older,
  timestamp-less lines unchanged.
- Let a test supply a fixed point in time so the exact timestamp written
  and displayed is assertable.

**Non-Goals:**
- Rewriting or migrating existing lines in `greetings.txt`.
- Timezone handling beyond the JVM's default zone (no configurable zone).
- Changing the history size limit or ordering behavior already specified
  in `greeting-history-display`.

## Decisions

- **Clock injection via `java.time.Clock`**: pass a `Clock` parameter into
  `run` (and from there into `appendGreeting`) instead of calling
  `LocalDateTime.now()` directly. `main` supplies `Clock.systemDefaultZone()`;
  a Spock spec supplies `Clock.fixed(...)`. This is the standard JDK seam
  for testable time and needs no new dependency.
  - Alternative considered: pass a `Supplier<LocalDateTime>` — rejected,
    `Clock` is the conventional, more discoverable choice for this in the
    JDK.
- **Timestamp detection on read is prefix-based, not stored as metadata**:
  a stored line is treated as timestamped when its first 16 characters
  parse as `yyyy-MM-dd HH:mm` (via `DateTimeFormatter`) followed by a
  space; otherwise the whole line is treated as untimestamped greeting
  text. No file format version marker is introduced. This keeps old lines
  working with no migration step, matching the proposal's compatibility
  requirement.
  - Alternative considered: detect by column count / regex on the whole
    line — rejected as more fragile than attempting a strict parse of the
    fixed-width prefix.
- **Display shows stored lines as-is**: since a timestamped line already
  contains its prefix as stored text, the display path does not need to
  reformat anything — it simply prints each history line unchanged. The
  "does this line have a timestamp" distinction only matters for parsing
  order/greeting-number logic, not for rendering.

## Risks / Trade-offs

- [A greeting whose name happens to start with something matching
  `yyyy-MM-dd HH:mm ` could be misread as timestamped] → Out of scope:
  greetings are always of the form `Hello, <name>!`, which cannot match
  the numeric timestamp pattern, so no realistic input collides.
- [`Clock` threading through `run`/`appendGreeting` changes their method
  signatures] → Both are package-private static helpers already called
  only from `main` and the test spec, so updating call sites is a
  contained, mechanical change.
