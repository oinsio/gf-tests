## Context

`HelloWorld.run` reads the full history via `readHistory` and prints it with
`history.forEach(out::println)`. See proposal.md - Why.

## Goals / Non-Goals

**Goals:**
- Cap the printed history section at the last 5 entries.
- Print an exact `и ещё N ранее` summary line when entries are omitted.

**Non-Goals:**
- Changing what is stored in `greetings.txt` or how greetings are appended.
- Changing the "Это приветствие номер N." counting logic.
- Adding configuration for the display limit (hardcode 5, per the task).

## Decisions

- Compute the displayed slice from the already-loaded `List<String> history`
  in `run` (`history.subList(max(0, size - 5), size)`), rather than changing
  `readHistory` to limit what it reads from disk. `readHistory` still returns
  the full list, since its size is also used for the greeting number — only
  the printing step is limited. Alternative considered: limiting inside
  `readHistory` was rejected because callers need both the full count and the
  limited display slice.
- Print the summary line immediately before the shown greetings, only when
  `history.size() > 5`, computing `N = history.size() - 5`.

## Risks / Trade-offs

- [Off-by-one in the omitted count] → covered by dedicated scenarios in
  `greeting-history/spec.md` for exactly 5 and more-than-5 cases; tasks.md
  requires a Spock test for the boundary (5 vs. 6 lines).
