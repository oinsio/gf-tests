# Acceptance criteria for the implement stage

- The change does what the task asked, and nothing more.
- New or changed behaviour is covered by a Spock spec under
  `src/test/groovy/com/example/`.
- Existing tests and public behaviour are not broken.
- Code style matches the surrounding code.

Judge by reading the source and the diff only. Do not run the build or the
tests: `./gradlew test` has already been run as a separate check before you,
and a green build is a precondition of your review, not part of it.
