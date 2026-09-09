## Context

`GreetingStats.run` (`src/main/java/com/example/GreetingStats.java`) already
reads `greetings.txt` via `HelloWorld.readHistory`, which returns raw lines
that may carry a `yyyy-MM-dd HH:mm ` timestamp prefix (see the
`greeting-timestamp` capability). `HelloWorld` has a private
`parseHistoryLine` helper that strips that prefix and returns the bare
greeting text (`Hello, <name>!` or `Hello, World!`). Extracting a name
requires further stripping the `Hello, ` prefix and trailing `!`.

## Goals / Non-Goals

**Goals:**
- Reuse the existing timestamp-stripping logic instead of duplicating the
  timestamp-detection rule in `GreetingStats`.
- Keep name extraction and deduplication simple and in step with how
  `counts` is already built (single pass over the history).

**Non-Goals:**
- Changing how names are computed from CLI arguments (`greetingFor`) or how
  greetings are saved — unaffected by this change.
- Changing the existing total-count or most-frequent-greeting output.

## Decisions

- **Expose a package-private name-extraction helper on `HelloWorld`**
  (e.g. `static String nameFromGreeting(String greeting)`) that strips the
  `Hello, ` / `!` wrapper, and reuse the existing timestamp-stripping logic
  (promoting `parseHistoryLine`/`HistoryEntry.text()` to package-private, or
  adding a small `static String stripTimestamp(String line)` helper next to
  it) so `GreetingStats` never re-implements the `yyyy-MM-dd HH:mm ` prefix
  check. Alternative considered: duplicate the prefix check in
  `GreetingStats` — rejected, since the two classes would then need to agree
  on the timestamp format independently.
- **Track names with a `LinkedHashSet<String>` built in the same loop that
  already builds `counts`** (one pass over `history`), preserving
  first-appearance order and deduplicating for free. Alternative considered:
  a `List` with a `contains` check — rejected as needlessly O(n²) for no
  benefit.
- **Print the list unconditionally whenever stats are shown** (i.e. inside
  the existing `if (history.isEmpty())` guard's else-branch), joined with
  `", "` via `String.join`, on its own line after the existing two output
  lines.

## Risks / Trade-offs

- [Widening `HelloWorld`'s history-parsing helpers to package-private
  surfaces internal parsing details to another class] → Both classes already
  live in `com.example` and `GreetingStats` already depends on
  `HelloWorld.readHistory`/`appendGreeting`; this is consistent with the
  existing coupling, not a new one.
