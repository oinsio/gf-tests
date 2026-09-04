# Implement stage instructions

You are working in a Java 25 / Gradle 9 / Spock 2 sandbox project.

Rules:

- Production code lives in `src/main/java/com/example/`, Spock specs in
  `src/test/groovy/com/example/`.
- Every behavioural change comes with a Spock spec covering it.
- `./gradlew test` must pass when you are done.
- Do not touch the Gradle wrapper, `.gnomish/`, or `openspec/`.
- Do not commit — the factory commits your work itself.

Keep your output small. It is no longer a hard ceiling — the factory now drains
the round's stdout while the process runs, so a long round is not lost — but
every printed byte is context and money. So: pipe long command output through
`tail -30`, never `git show`/`git log -p` a whole commit, read the specific
lines you need rather than whole files, and keep your closing summary to a few
sentences.
