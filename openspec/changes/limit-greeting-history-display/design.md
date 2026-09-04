## Context

`HelloWorld.run` reads the full history via `readHistory` and prints every
line with `history.forEach(out::println)`. See proposal.md - Why.

## Goals / Non-Goals

**Goals:**
- Cap the printed history to the last 5 entries.
- Report how many older entries were hidden, with exact wording.

**Non-Goals:**
- Changing how history is stored in `greetings.txt` (still append-only, plain
  text, one greeting per line).
- Changing the greeting-number counting logic beyond keeping it based on the
  full history.
- Adding a configurable limit; 5 is a fixed constant.

## Decisions

- Introduce a constant `HISTORY_DISPLAY_LIMIT = 5` in `HelloWorld` rather than
  hardcoding the number, so the value has one obvious source.
- Compute the displayed slice as `history.size() > 5 ? history.subList(history.size() - 5, history.size()) : history`
  and print the `и ещё N ранее.` line only when `history.size() > 5`, with
  `N = history.size() - 5`. Printing the summary line before the sliced
  entries mirrors how a truncated scrollback is usually read (context first,
  detail after).
- Keep `readHistory` unchanged (still returns the full list) so the greeting
  number calculation (`history.size() + 1`) is untouched and correct.

## Risks / Trade-offs

- [Off-by-one in the slice/count math] → Cover both boundary cases (`size ==
  5` and `size == 6`) in tests to pin the exact cutover point.
