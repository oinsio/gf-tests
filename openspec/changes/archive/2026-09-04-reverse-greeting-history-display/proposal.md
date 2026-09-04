## Why

The greeting history is printed oldest-first, so the most recent entry — the
one the user just cares about most — ends up buried at the bottom of the
block. Printing the history newest-first surfaces it immediately.

## What Changes

- Reverse the order of the printed history lines so the most recent entry is
  printed first and the oldest of the displayed entries is printed last.
- Keep showing at most the same number of entries as today (5).
- Move the `и ещё N ранее.` summary line to after the displayed history lines,
  since it now refers to entries older than everything just shown above it.
- When the history is empty, print no history lines and no summary line
  (unchanged).
- The greeting number reported at the end (`Это приветствие номер N.`)
  continues to count the full history, not just the displayed slice
  (unchanged).

## Capabilities

### Modified Capabilities
- `greeting-history-display`: the display order of printed history lines and
  the position of the "и ещё N ранее." summary line relative to them.

## Impact

- `src/main/java/com/example/HelloWorld.java`: `run` method's
  history-printing logic.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: tests asserting the
  printed order and the position of the summary line will need to reflect the
  new newest-first output.
