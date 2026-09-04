## 1. Limit printed history to the last 5 entries

- [x] 1.1 Change `HelloWorld.run` to print at most the last 5 entries of
  `history`, in original chronological order, when printing "История
  приветствий:". Verify with
  `src/test/groovy/com/example/HelloWorldSpec.groovy`: "run prints previous
  greetings from the history file and the correct greeting number" (extend or
  add a case with 5 lines) still passes with all 5 shown.
- [x] 1.2 Add a Spock test for a history of 6+ lines confirming only the last
  5 lines are printed (in order) and earlier lines are not printed. Verify
  with a new test in `src/test/groovy/com/example/HelloWorldSpec.groovy`,
  e.g. "run prints only the last 5 greetings when history is longer than 5".

## 2. Summarize omitted greetings

- [x] 2.1 Print `и ещё N ранее` before the shown greetings whenever
  `history.size() > 5`, with N equal to `history.size() - 5`. Verify with a
  new test in `src/test/groovy/com/example/HelloWorldSpec.groovy`, e.g. "run
  prints и ещё N ранее summary when history exceeds 5 entries", asserting the
  exact line for an 8-line history (`и ещё 3 ранее`).
- [x] 2.2 Confirm no summary line is printed when history has exactly 5 or
  fewer lines. Verify with a new/extended test in
  `src/test/groovy/com/example/HelloWorldSpec.groovy`, e.g. "run does not
  print и ещё N ранее when history has 5 or fewer entries", for both the
  empty-history case and a 5-line case.

## 3. Preserve greeting numbering

- [x] 3.1 Confirm "Это приветствие номер N." still uses the true total
  history size (unaffected by the 5-item display cap). Verify with the
  existing test "run prints previous greetings from the history file and the
  correct greeting number" plus a new case using more than 5 prior
  greetings, e.g. "run reports the correct greeting number when history
  exceeds 5 entries", in
  `src/test/groovy/com/example/HelloWorldSpec.groovy`.
