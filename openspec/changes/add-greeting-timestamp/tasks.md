## 1. Injectable clock and timestamped save

- [x] 1.1 Add a `Clock` parameter to `appendGreeting` and format
  `LocalDateTime.now(clock)` as `yyyy-MM-dd HH:mm` prefixed to the greeting
  before writing; update `run` to accept and pass through a `Clock`, and
  update `main` to pass `Clock.systemDefaultZone()`. Verify with
  `HelloWorldSpec`: a test using `Clock.fixed(...)` asserts the exact saved
  line is `<timestamp> <greeting>`.

## 2. Backward-compatible history parsing and display

- [x] 2.1 Add a history-line parsing helper that splits a leading
  `yyyy-MM-dd HH:mm ` timestamp from the rest of the line when present, and
  treats the whole line as the greeting (no timestamp) otherwise. Verify
  with `HelloWorldSpec`: tests assert correct parsing of both a
  timestamped line and a legacy untimestamped line.
- [x] 2.2 Update `run`'s history printing to reconstruct each displayed
  line via the parsed entry (`timestamp + " " + greeting` when a timestamp
  is present, else just `greeting`), keeping existing ordering, 5-entry
  truncation, "and N more earlier." and greeting-number counting behavior
  unchanged. Verify with `HelloWorldSpec`: existing history-ordering and
  truncation tests still pass with a mix of timestamped and legacy lines
  in the history file.

## 3. Regression check

- [x] 3.1 Update existing `HelloWorldSpec` assertions that check exact
  saved/printed line content (e.g. `Files.readString(outputFile).contains(...)`,
  `appendGreeting` round-trip tests) to account for the timestamp prefix
  using a fixed injected `Clock`. Verify by running the full Spock suite
  (`./gradlew test`) and confirming all tests pass.
