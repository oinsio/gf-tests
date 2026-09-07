## Context

`HelloWorld.main` currently always builds a greeting from `args`, calls `run(...)`, which prints the greeting, prints history, and prompts to save. See proposal.md for why a separate stats path is needed.

## Goals / Non-Goals

**Goals:**
- Keep `HelloWorld` limited to dispatching on the `--stats` argument; all statistics logic lives in `GreetingStats`.
- Reuse the existing `greetings.txt` line-per-greeting format; no new file format.

**Non-Goals:**
- No change to the normal (non-`--stats`) greeting/history/save flow.
- No support for combining `--stats` with a name argument or other flags; presence of `--stats` anywhere in `args` always short-circuits to stats output.

## Decisions

- **Dispatch location**: `HelloWorld.main` checks whether `args` contains `--stats` before computing a greeting. If so, it delegates to `GreetingStats` and returns without calling `run`. This keeps `main` as the single dispatch point and `run`/`greetingFor` untouched.
- **`GreetingStats` API**: a static method taking the greetings file `Path` and a `PrintStream`, mirroring the existing `HelloWorld.readHistory(Path)` / `run(..., PrintStream, ...)` pattern already used and already tested via dependency injection in `HelloWorldSpec`. This keeps the new class testable without touching stdin/stdout globally.
- **Frequency computation**: reuse `HelloWorld.readHistory(Path)` to read lines, then tally occurrences with a `Map<String, Long>`. Ties for most-frequent greeting are broken by first occurrence in the file (stable iteration order), since the task does not specify tie-breaking and any deterministic rule satisfies "the most frequent greeting".
- **Empty/missing file**: reuse the same emptiness check already implicit in `readHistory` (`Files.exists` + empty list) rather than duplicating file-existence logic.

## Risks / Trade-offs

- [Tie-breaking for equally-frequent greetings is unspecified by the task] → Documented as first-occurrence-wins; acceptable since the task only asks for "the most frequent greeting" without tie-break rules.
