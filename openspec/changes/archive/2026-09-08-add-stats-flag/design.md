## Context

`HelloWorld.main` currently always computes a greeting from `args` and runs
the full greet/history/save flow via `run(...)`. See proposal.md - Why for
the motivation for adding a statistics mode. `HelloWorld` already reads
`greetings.txt` via `readHistory`, which is a useful precedent for handling
a missing file as an empty list.

## Goals / Non-Goals

**Goals:**
- Keep `HelloWorld` limited to argument dispatch: detect `--stats` and
  delegate everything else to a dedicated class.
- Put the statistics computation and formatting in one class that can be
  tested independently of `HelloWorld`'s I/O plumbing.

**Non-Goals:**
- Combining `--stats` with other flags or a name argument.
- Changing the format or location of `greetings.txt`.

## Decisions

- Introduce `GreetingStats` in `com.example` with a static method that takes
  the greetings file path (and/or an already-read list of lines, mirroring
  `HelloWorld.readHistory`) and a `PrintStream`, and prints the statistics
  or the `No greetings saved yet.` fallback. `HelloWorld.main` checks
  `args.length == 1 && args[0].equals("--stats")` before falling back to the
  existing `greetingFor`/`run` path, and calls `GreetingStats` instead.
  Alternative considered: keep the logic inline in `HelloWorld` — rejected
  because the task requires the statistics logic to live in its own class
  and because it keeps `HelloWorld` focused on one responsibility.
- Most-frequent greeting is computed by counting occurrences of each
  distinct line and picking the line with the highest count; ties are
  broken arbitrarily (e.g., first one encountered), since the task does not
  specify tie-breaking behavior beyond "the most frequent greeting".

## Risks / Trade-offs

- [Ambiguous tie-breaking for equally-frequent greetings] → Spec and tests
  only assert that *a* correct greeting with the correct count is reported
  in tie scenarios, not which specific one.
