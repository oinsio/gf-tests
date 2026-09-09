## Context

`GreetingStats.run` (`src/main/java/com/example/GreetingStats.java`) already
reads the saved greeting lines via `HelloWorld.readHistory` and counts them
in a `LinkedHashMap<String, Integer>`. Every saved greeting is produced by
`HelloWorld.greetingFor`, so each history line has the fixed shape
`Hello, <name>!` (the default, unnamed case saves `Hello, World!`, so
"World" is treated as a name like any other).

## Goals / Non-Goals

**Goals:**
- Extract the name from each saved greeting line and print the
  deduplicated set once, in addition to the existing total/most-frequent
  output.

**Non-Goals:**
- No change to how greetings are recorded or to the file format.
- No change to the existing total/most-frequent output.
- Not attempting to handle greeting lines that don't match the
  `Hello, <name>!` shape differently from any other line in history —
  the codebase has one producer of these lines, so no such lines exist.

## Decisions

- Reuse the `counts` map already built in `GreetingStats.run`: its key set
  is exactly the distinct saved greeting lines. Derive each name by
  stripping the fixed `"Hello, "` prefix and trailing `"!"` from each key,
  collecting into a `LinkedHashSet<String>` to dedupe while preserving
  first-seen order. This avoids a second pass over `history`.
- Print names in the order they were first greeted (oldest first), joined
  with `, `, on a single line prefixed with a label (e.g.
  `Greeted: World, Alice, Bob`) — consistent with the terse, single-line
  style of the existing stats output.

## Risks / Trade-offs

- [Stripping a fixed `"Hello, "`/`"!"` prefix/suffix would misparse a
  greeting line that doesn't follow that shape] → Not a real risk today:
  `HelloWorld.appendGreeting` only ever persists strings produced by
  `greetingFor`, which always has this shape.
