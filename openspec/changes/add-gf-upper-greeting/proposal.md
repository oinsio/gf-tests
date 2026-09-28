# Proposal

## Why

Some users run the application in contexts (scripts, terminals with poor
contrast, accessibility needs) where an upper-case greeting is easier to
read or parse. There is currently no way to request this without changing
the code.

## What Changes

- When the `GF_UPPER` environment variable is set to `1`, the greeting line
  printed to stdout is upper-cased.
- When `GF_UPPER` is unset, empty, or set to any value other than `1`, the
  greeting prints unchanged, as today.
- Only the printed greeting line is affected; greeting history display and
  the greeting text appended to `greetings.txt` are unchanged.

## Capabilities

### New Capabilities

(none)

### Modified Capabilities

- `greeting`: adds a requirement that the printed greeting is upper-cased
  when the `GF_UPPER` environment variable is set to `1`.

## Impact

- `src/main/java/com/example/HelloWorld.java`: the greeting must be
  upper-cased before it is printed, based on the `GF_UPPER` environment
  variable, without affecting the value written to `greetings.txt` or used
  for history counting.
