## Why

The application always prints and records `Hello, World!`, regardless of who is
running it. Users want to pass a name on the command line (e.g.
`./gradlew run --args="Alice"`) and have that name appear both in the printed
greeting and in the `greetings.txt` history, so the greeting is personalized
end-to-end instead of only via the internal `greet(String)` helper.

## What Changes

- `HelloWorld.main` reads the process command-line arguments and builds the
  greeting from them instead of always using the fixed `"Hello, World!"`
  string.
- No arguments → behavior is unchanged: the greeting is `Hello, World!`.
- One or more arguments → all arguments are joined with a single space and
  trimmed to form the name; the greeting becomes `Hello, <name>!`.
- If the joined/trimmed name is empty (e.g. the caller passed only blank
  arguments), the greeting falls back to `Hello, World!` — same as no
  arguments.
- The resulting greeting (not a hardcoded constant) is the one shown in the
  printed history, counted for "greeting number N", and appended to
  `greetings.txt` when the user agrees to save.

## Capabilities

### New Capabilities
- `greeting`: the greeting text shown, counted in history, and saved to
  `greetings.txt` is derived from command-line arguments (with defined rules
  for no args, blank/whitespace args, and multiple args) instead of being a
  fixed constant.

### Modified Capabilities
(none)

## Impact

- `src/main/java/com/example/HelloWorld.java`: `main` gains argument handling;
  the greeting used by `run`/history/save flow becomes a computed value
  instead of the `GREETING` constant.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: new/updated Spock
  scenarios covering argument-driven greetings.
