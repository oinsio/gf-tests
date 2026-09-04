## Context

`HelloWorld.run` currently takes the last `HISTORY_DISPLAY_LIMIT` (5) lines
of `history` in file order (oldest first) and prints them as-is, with the
`и ещё N ранее.` summary printed before them when truncated. See
`specs/greeting-history-display/spec.md` for the exact required behavior.

## Goals / Non-Goals

**Goals:**
- Print the displayed slice newest-first without changing which entries are
  selected or how the greeting number is computed.

**Non-Goals:**
- Changing the on-disk format or order of `greetings.txt` — entries are still
  appended oldest-to-newest; only the printed presentation changes.

## Decisions

- Reverse only the `displayedHistory` list right before printing (e.g. via
  `Collections.reverse` on a copy, or `stream().sorted` in reverse index
  order), leaving `readHistory`, the slicing logic, and the greeting-number
  calculation untouched. Reversing the already-sliced 5-entry list is
  simpler and cheaper than reversing the full history first and re-deriving
  the slice.
- Move the `и ещё N ранее.` print statement to after the loop that prints
  `displayedHistory`, matching the new reading order (most recent first,
  then a note about what's further back/older, which now sits below).

## Risks / Trade-offs

- Existing Spock tests assert oldest-first order and the summary line before
  the entries; they must be updated to match the new order and position, or
  they will fail after this change ships.
