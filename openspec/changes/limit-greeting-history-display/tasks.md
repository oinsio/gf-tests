## 1. Truncated history printing

- [ ] 1.1 Add a `HISTORY_DISPLAY_LIMIT = 5` constant and change `run` to
      print only the last 5 history entries when the history has more than 5,
      keeping their original order — verified by
      `HelloWorldSpec`: "run prints only the last 5 history entries when more than 5 exist"
- [ ] 1.2 Print all history entries unchanged when the history has 5 or fewer
      entries — verified by `HelloWorldSpec`: "run prints all history entries when there are 5 or fewer"

## 2. Hidden-count summary line

- [ ] 2.1 Print `и ещё N ранее.` before the displayed history entries when
      N = history.size() - 5 is greater than 0 — verified by
      `HelloWorldSpec`: "run prints the hidden-count summary line when history exceeds 5 entries"
- [ ] 2.2 Omit the `и ещё N ранее.` line when the history has exactly 5,
      fewer than 5, or 0 entries — verified by
      `HelloWorldSpec`: "run omits the hidden-count summary line when history has 5 or fewer entries" and
      `HelloWorldSpec`: "run omits the hidden-count summary line when history is empty"

## 3. Greeting number stays based on full history

- [ ] 3.1 Confirm `Это приветствие номер N.` still reports the full history
      size plus one even when the printed history is truncated — verified by
      `HelloWorldSpec`: "run reports the correct greeting number when history is truncated"
