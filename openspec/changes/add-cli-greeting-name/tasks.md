## 1. Compute the greeting from arguments

- [x] 1.1 Add a `greetingFor(String[] args)` helper to `HelloWorld` that joins
      `args` with a single space, trims the result, and returns
      `"Hello, " + name + "!"`, falling back to `"Hello, World!"` when the
      trimmed name is empty; verified by
      `HelloWorldSpec` scenarios `greetingFor returns the default greeting
      with no arguments`, `greetingFor returns a personalized greeting for a
      single argument`, `greetingFor trims surrounding whitespace from a
      single argument`, `greetingFor joins multiple arguments with a space`,
      `greetingFor falls back to the default greeting for a single blank
      argument`, and `greetingFor falls back to the default greeting for a
      single whitespace-only argument`.

## 2. Thread the computed greeting through `run`

- [x] 2.1 Change `run`'s signature to accept the greeting text as its first
      parameter (`run(String greeting, InputStream in, PrintStream out, Path
      outputFile)`) and use it wherever it currently reads the `GREETING`
      constant (printing, history count, save-to-file); update `main` to call
      `run(greetingFor(args), System.in, System.out, GREETINGS_FILE)`;
      verified by updating every existing direct `HelloWorld.run(...)` call
      in `HelloWorldSpec` to pass `"Hello, World!"` explicitly and confirming
      those pre-existing scenarios (`should ask whether to save the greeting
      to a file`, `should save the greeting to a file when the user agrees`,
      `should not save the greeting to a file when the user declines`, `run
      prints empty history and greeting number 1 when the file does not
      exist`, `run prints previous greetings from the history file and the
      correct greeting number`) still pass.

## 3. Cover argument-driven end-to-end behavior

- [x] 3.1 Add `HelloWorldSpec` scenarios that call `run` with a
      `greetingFor`-computed greeting to verify a personalized greeting is
      appended to `greetings.txt` when the user agrees to save
      (`personalized greeting is appended to the file when the user agrees`)
      and that it counts correctly in printed history alongside prior
      entries (`personalized greeting counts correctly in the printed
      history`).
- [x] 3.2 Add a `HelloWorldSpec` scenario that calls `HelloWorld.main` with a
      single-element `args` array (e.g. `["Alice"]` with `System.in` closed)
      and asserts the captured stdout contains `Hello, Alice!` instead of
      `Hello, World!` (`main prints a personalized greeting when a name
      argument is supplied`), and update the existing `should print hello
      world message` scenario if needed so it still passes with `main`
      called with no arguments.

## 4. Full verification

- [x] 4.1 Run `./gradlew test` and confirm all `HelloWorldSpec` scenarios
      pass, including the new argument-driven ones.
