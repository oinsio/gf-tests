# Design

## Context

`HelloWorld.run` computes the `greeting` string and immediately does three
things with it: prints it via `out.println(greeting)`, counts it in the
history, and appends it (unchanged) to `greetings.txt` if the user agrees.
See `openspec/specs/greeting/spec.md` for the requirements this change adds.

## Goals / Non-Goals

**Goals:**
- Upper-case only the printed greeting line when `GF_UPPER=1`.

**Non-Goals:**
- Changing the greeting text written to `greetings.txt`.
- Changing the history display lines (they are read back from the file,
  not re-derived from the current `greeting` variable).
- Supporting values other than the literal `1` (e.g. `true`, `yes`).

## Decisions

- Read `GF_UPPER` with `System.getenv("GF_UPPER")` and compare for equality
  with `"1"`. Any other value (including unset/null) leaves the greeting
  unchanged.
- Upper-case only the value passed to `out.println(greeting)` in
  `HelloWorld.run`, not the `greeting` variable itself, so the value later
  passed to `appendGreeting` stays untouched.

## Risks / Trade-offs

- [Setting `GF_UPPER` for a Spock test means mutating the real process
  environment, which the JDK does not support cleanly] → Route the check
  through a small package-private method (e.g.
  `HelloWorld.shouldUpper(String gfUpperValue)`) that `run` calls with
  `System.getenv("GF_UPPER")`. Tests call `shouldUpper`/the upper-casing
  logic directly with an arbitrary string, without touching the real
  environment.
