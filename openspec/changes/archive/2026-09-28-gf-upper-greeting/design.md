## Context

`HelloWorld.greetingFor(args)` computes the greeting text; `HelloWorld.run(...)`
then prints it, includes it in history, and offers to save it. See
proposal.md for motivation.

## Goals / Non-Goals

**Goals:**
- Upper-case the greeting everywhere it is used (print, history count,
  saved file) when `GF_UPPER=1`.

**Non-Goals:**
- No new command-line flag; this is environment-variable driven only.
- No change to how greeting history is parsed, displayed, or timestamped.
- No change to `--stats` behavior.

## Decisions

- Read `GF_UPPER` via `System.getenv("GF_UPPER")` and apply
  `String::toUpperCase` to the result of `greetingFor(args)` in `main`,
  before it is passed to `run`. This keeps the transformation in one place
  so every downstream use (print, history, save) sees the already-upper-cased
  greeting, matching the existing `greeting` requirement that a single
  computed greeting value drives all three behaviors.
  - Alternative considered: upper-case inside `run(...)`. Rejected because
    `run` is also exercised directly by tests with an explicit greeting
    string; keeping the environment lookup in `main` (alongside the existing
    `--stats` argument check) avoids adding environment-variable coupling to
    the more general `run` method.
- Compare the environment variable value with `.equals("1")` (exact match,
  no trimming or case-insensitivity) to keep the trigger condition simple
  and unambiguous, consistent with the proposal's scenario for non-`1`
  values leaving the greeting unchanged.

## Risks / Trade-offs

- [Tests that set environment variables can be awkward in the JVM] →
  `greetingFor` and the upper-casing decision are simple enough to test as
  a plain string transformation; Spock specs can call the small unit
  directly (e.g. a helper that takes the env value as a parameter) rather
  than mutating real process environment variables.
