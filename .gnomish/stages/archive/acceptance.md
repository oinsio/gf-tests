# Acceptance criteria for the archive stage

- The change this task branch introduced now lives under
  `openspec/changes/archive/<date>-<name>/`, and `openspec/changes/` contains
  nothing else besides `archive/`.
- The archived change is complete: every task in its `tasks.md` is ticked.
- Its spec deltas were promoted into `openspec/specs/`: each capability the
  change added or modified is reflected there, and the promoted text describes
  the behaviour the implement stage actually built. A change marked
  `skip_specs: true` correctly promotes nothing.
- The archiving was done by the OpenSpec CLI, not by hand: the archived files
  are the change's own files moved intact, and `openspec/specs/` was not
  hand-authored. Small fixes to a generated spec file are acceptable only if
  the agent's summary explains why validation demanded them.
- Nothing outside `openspec/` changed. In particular `src/`, `build.gradle` and
  the Gradle wrapper are untouched by this stage.
- No other project's active change was archived or otherwise disturbed.

Judge by reading the OpenSpec tree and the diff only. Do not run the build, the
tests or the OpenSpec CLI: those checks have already run before you, and their
green result is a precondition of your review, not part of it.
