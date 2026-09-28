## Why

Some environments (e.g. terminals with limited styling, or scripts that grep
for greetings) want the greeting rendered in upper case. The application has
no way to request this today; adding an opt-in environment variable lets
callers request upper-case output without changing command-line arguments.

## What Changes

- When the `GF_UPPER` environment variable is set to `1`, the greeting
  printed by the application is converted to upper case before it is printed,
  counted in history, and saved.
- When `GF_UPPER` is unset, empty, or set to any value other than `1`, the
  greeting is unchanged (current behavior).

## Capabilities

### New Capabilities
(none)

### Modified Capabilities
- `greeting`: adds a requirement that the greeting is upper-cased when the
  `GF_UPPER` environment variable is set to `1`.

## Impact

- `src/main/java/com/example/HelloWorld.java`: read the `GF_UPPER`
  environment variable and upper-case the computed greeting before it is
  printed, added to history, and saved to `greetings.txt`.
- `src/test/groovy/com/example/HelloWorldSpec.groovy`: new scenarios
  covering `GF_UPPER`.
