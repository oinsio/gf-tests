## Context

`HelloWorld.main` currently always runs the greet-and-save flow (see
`specs/greeting/spec.md`). `--stats` needs to be recognized before that flow
starts. `HelloWorld.readHistory(Path)` already reads `greetings.txt` into a
`List<String>` and is reused here rather than duplicated.

## Goals / Non-Goals

**Goals:**
- Keep `HelloWorld` limited to dispatching on `args`: either run the
  existing greet flow, or delegate to the new stats behaviour.
- Put all statistics computation and formatting in `GreetingStats`.

**Non-Goals:**
- No new CLI flags beyond `--stats`.
- No caching or performance work on `greetings.txt` reading.

## Decisions

- `HelloWorld.main` checks `args` for `--stats` first. If present, it calls
  a new static entry point, e.g. `GreetingStats.run(PrintStream out, Path
  file)`, and returns without touching the existing `greetingFor`/`run`
  flow. This mirrors the existing `run(...)` method's style of taking
  explicit `PrintStream`/`Path` parameters for testability.
- `GreetingStats` reuses `HelloWorld.readHistory(Path)` to load saved
  greetings rather than re-implementing file reading, since that method
  already handles the "file does not exist" case by returning an empty
  list.
- Tie-breaking for "most frequent greeting": when multiple greetings share
  the highest count, pick the one that appears first in the file. This is
  a simple, deterministic rule that doesn't need to be user-configurable.
- Alternative considered: putting the `--stats` branch and counting logic
  directly in `HelloWorld`. Rejected because the task explicitly asks for
  a separate class and because it keeps `HelloWorld` a thin dispatcher.

## Risks / Trade-offs

- [Large `greetings.txt` files could make counting slow] → Not a concern
  for this project's scale (a local sandbox file); no mitigation needed.
