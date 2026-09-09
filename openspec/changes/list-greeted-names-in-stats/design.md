## Context

`GreetingStats.run` (see `specs/greeting-stats/spec.md`) already reads
saved greetings via `HelloWorld.readHistory(Path)` and computes the total
count and most frequent greeting. Every saved greeting is a full sentence
in the form `Hello, <name>!` (see `specs/greeting/spec.md`), so the name
can always be recovered from a saved line by stripping the `Hello, `
prefix and trailing `!`.

## Goals / Non-Goals

**Goals:**
- Extract the name from each saved greeting and print the deduplicated,
  first-occurrence-ordered list as part of `--stats` output.

**Non-Goals:**
- No change to how names are extracted from timestamp prefixes — this
  reuses `HelloWorld.readHistory(Path)`, which already returns raw saved
  lines (greeting text only; timestamp handling is `HelloWorld`'s
  concern for history display, not statistics).
- No case-insensitive or fuzzy matching of names; deduplication is exact
  string equality.

## Decisions

- Name extraction: strip the literal `Hello, ` prefix and trailing `!`
  from each saved greeting line to get the name, mirroring how
  `HelloWorld.greetingFor` builds the greeting in the first place.
- Deduplication + ordering: iterate saved greetings in file order, keep
  each name the first time it's seen, using a `LinkedHashSet<String>`
  (or equivalent) to get both dedup and insertion order for free.
- Output format: a single line `Greeted: ` followed by the names joined
  with `, `, appended after the existing total/most-frequent lines. A
  single line keeps the format consistent with the rest of `--stats`
  output, which is short summary lines rather than a multi-line list like
  the startup history.
- Alternative considered: printing one name per line (mirroring the
  greeting history display). Rejected — the stats output is meant to be a
  compact summary, and the greeted-names list is expected to be a short,
  read-at-a-glance line, not a scrollable history.

## Risks / Trade-offs

- [A saved line that doesn't match `Hello, <name>!` could break name
  extraction] → Not a concern: every line in `greetings.txt` is written by
  `HelloWorld.appendGreeting` with a greeting built by `greetingFor`,
  which always produces this exact shape.
