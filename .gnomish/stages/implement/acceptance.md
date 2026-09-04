# Acceptance criteria for the implement stage

- The code implements the OpenSpec change under `openspec/changes/` that the
  previous stage produced: every requirement in its spec deltas is realised,
  and every task in its `tasks.md` is done and ticked.
- The change does what the task asked, and nothing more — no scope the specs do
  not call for.
- New or changed behaviour is covered by a Spock spec under
  `src/test/groovy/com/example/`.
- Existing tests and public behaviour are not broken.
- The contract from the previous stage is intact: `proposal.md`, `design.md`
  and the files under `specs/` are unchanged — only `tasks.md` checkboxes moved.
- Code style matches the surrounding code.

Judge by reading the source, the OpenSpec change and the diff only. Do not run
the build or the tests: `./gradlew test` has already been run as a separate
check before you, and a green build is a precondition of your review, not part
of it.
