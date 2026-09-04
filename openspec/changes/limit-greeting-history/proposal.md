## Why

`greetings.txt` grows without bound, and the app currently prints the entire
history on every run (`HelloWorld.run` calls `history.forEach(out::println)`).
Once the file has more than a handful of lines, the printed history becomes
long and hard to read. We need to cap what is printed and summarize the rest.

## What Changes

- The printed history section shows only the last 5 greetings from the file
  (in their original, chronological order — oldest of the shown ones first,
  newest last).
- If the file has more than 5 lines, a summary line is printed before the
  shown greetings: `и ещё N ранее`, where N is the count of lines omitted
  (total lines minus 5).
- If the file has 5 or fewer lines, no summary line is printed and all lines
  are shown, matching current behavior.
- The "Это приветствие номер N." line continues to reflect the true total
  count of greetings (existing history size + 1), unaffected by the display
  limit.

## Capabilities

### New Capabilities
- `greeting-history`: display rules for the greeting history section printed
  on each run, including the 5-item cap and the "и ещё N ранее" summary line.

### Modified Capabilities
(none — no existing specs under `openspec/specs/`)

## Impact

- `src/main/java/com/example/HelloWorld.java`: the `run` method's history
  printing logic (currently `history.forEach(out::println)`).
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: existing tests that
  assert on printed history output remain valid for histories of 5 or fewer
  entries; new tests are needed for histories longer than 5 entries.
