## 1. Injectable clock and timestamped save

- [x] 1.1 Add a `java.time.Clock`-accepting overload of `appendGreeting` that
      prefixes the greeting with the current time in `yyyy-MM-dd HH:mm`
      format followed by a single space, keeping the existing no-clock
      overload delegating to the system clock; verify with new cases in
      `HelloWorldSpec.groovy` asserting an exact fixed-clock timestamp is
      written (e.g. `appendGreeting writes a timestamp prefix from the
      injected clock`).
- [x] 1.2 Update `run(...)` to accept and pass through the clock to the save
      path so a caller (and the Spock spec) can fix the save time end to
      end; verify with a `HelloWorldSpec.groovy` case asserting the saved
      line in `greetings.txt` has the exact expected timestamp prefix after
      calling `run` with a fixed clock and a "yes" answer (e.g. `run saves
      the greeting with the timestamp from the injected clock`).

## 2. Reading and displaying timestamped and legacy lines

- [x] 2.1 Update `readHistory`/the display logic in `run(...)` to parse a
      line's leading 16 characters as `yyyy-MM-dd HH:mm` when possible and
      keep the timestamp attached to the greeting text for display, leaving
      lines that don't parse as untouched legacy text; verify with
      `HelloWorldSpec.groovy` cases asserting a timestamped line prints with
      its timestamp intact (e.g. `run displays a timestamped history line
      with its timestamp`).
- [x] 2.2 Verify legacy (untimestamped) lines already covered by existing
      specs such as `readHistory returns the lines already stored in the
      file` and `run prints previous greetings from the history file and
      the correct greeting number` still pass unchanged, and add a mixed
      case with one legacy line and one timestamped line asserting both
      display correctly (e.g. `run displays a mix of legacy and timestamped
      history lines`).

## 3. Regression pass

- [x] 3.1 Update existing specs whose assertions hard-code the old
      untimestamped save format (`appendGreeting creates the file when it
      does not exist`, `appendGreeting continues writing into the same
      file`, `personalized greeting is appended to the file when the user
      agrees`) to account for the timestamp prefix, then run
      `./gradlew test` and verify the full suite passes.
