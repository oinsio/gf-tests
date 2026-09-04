## Why

The greeting history is printed in full on every run. As `greetings.txt` grows,
this output becomes long and pushes the "Save greeting to file?" prompt off
screen, making the program harder to use. Limiting the printed history to the
most recent entries keeps the output short while still telling the user how
many older greetings exist.

## What Changes

- When printing the greeting history, show only the last 5 entries (the 5 most
  recent, in chronological order).
- If the history has more than 5 entries, print a summary line before the
  listed entries: `и ещё N ранее.`, where N is the number of older entries not
  shown.
- If the history has 5 or fewer entries, print all of them and omit the
  summary line (current behavior, unchanged).
- The greeting number reported at the end (`Это приветствие номер N.`) keeps
  counting the full history, not just the displayed slice.

## Capabilities

### New Capabilities
- `greeting-history-display`: rules for how many past greetings are printed
  and how the count of hidden older greetings is reported.

### Modified Capabilities
(none — no existing specs precede this change)

## Impact

- `src/main/java/com/example/HelloWorld.java`: `run` method's history-printing
  logic.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: tests asserting the full
  history is printed will need to reflect the new truncated output.
