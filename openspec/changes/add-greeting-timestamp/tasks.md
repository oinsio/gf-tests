## 1. Timestamped save

- [ ] 1.1 Add a `Clock` parameter to `HelloWorld.appendGreeting` (and thread
      it through `run`, defaulting to `Clock.systemDefaultZone()` in `main`)
      that prefixes the saved line with `yyyy-MM-dd HH:mm ` from a
      `DateTimeFormatter`. Verify with a new Spock test in
      `src/test/groovy/com/example/HelloWorldSpec.groovy` that saves a
      greeting using a fixed `Clock` and asserts the exact prefixed line
      written to the file (e.g. `appendGreeting writes a timestamp prefix
      using the supplied clock`).
- [ ] 1.2 Verify existing save-related tests
      (`appendGreeting creates the file when it does not exist`,
      `appendGreeting continues writing into the same file`, and the
      `run ... save` tests) still compile and pass, updating their expected
      strings to include the timestamp prefix produced by the fixed/default
      clock used in each test.

## 2. Backward-compatible history display

- [ ] 2.1 Add a helper that recognizes whether a stored history line starts
      with a valid `yyyy-MM-dd HH:mm ` prefix and, if so, splits it into the
      timestamp and greeting text; otherwise treats the whole line as
      untimestamped greeting text. Verify with a new Spock test asserting
      the helper's output for a timestamped line, an untimestamped line, and
      an empty/edge-case line (e.g. `parses a timestamped history line into
      timestamp and text` / `treats a line without a timestamp prefix as
      plain greeting text`).
- [ ] 2.2 Update `run`'s history printing to use this helper so a
      timestamped line prints as `yyyy-MM-dd HH:mm <greeting>` and a
      legacy line prints as `<greeting>` unchanged. Verify with a new Spock
      test where the history file contains both a legacy untimestamped line
      and a timestamped line, asserting both are printed in their expected
      form (e.g. `run displays a mixed history of timestamped and legacy
      lines correctly`).
- [ ] 2.3 Verify existing history-printing tests
      (`run prints previous greetings from the history file...`, the
      5-entries and >5-entries truncation/ordering/count tests) still pass
      unchanged, since they build history via `appendGreeting` and only
      assert on greeting substrings/counts, not on raw line format.

## 3. Full verification

- [ ] 3.1 Run the full Spock suite (`./gradlew test`) and confirm all tests
      pass, including the new timestamp-save, timestamp-parsing, and
      mixed-history-display specs added above.
