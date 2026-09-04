# Acceptance criteria for the specify stage

Under `openspec/changes/` there is exactly one non-archived change directory,
holding the artifacts the `spec-driven` schema requires: `proposal.md`, spec
deltas under `specs/`, `design.md` and `tasks.md`. A change with no behavioural
delta may set `skip_specs: true` in its `.openspec.yaml` instead of shipping a
spec delta — but only when the task genuinely changes no behaviour.

- The proposal states why the change is needed and what changes, and its
  Capabilities section matches the spec files actually written.
- The specs describe observable behaviour, not implementation steps.
- `tasks.md` covers everything the proposal promises, in an order one
  implementer can follow, each task independently verifiable and naming the
  Spock spec that proves it.
- The change is scoped to the task and nothing beyond it.
- This stage wrote specifications only: the diff touches `openspec/` and
  nothing else — no `src/`, no build files.

Judge by reading the change directory and the diff only. Do not run the build,
the tests, or the OpenSpec CLI: `openspec validate --changes --strict` has
already been run as a separate check before you, and a passing validation is a
precondition of your review, not part of it.
