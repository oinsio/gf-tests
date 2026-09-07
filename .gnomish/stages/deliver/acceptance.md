# Acceptance criteria for the deliver stage

`pr-body.md` in the repository root holds the pull request body exactly as it
was published — a check before you has already confirmed that an open pull
request for this branch exists, targets `main`, carries a real title, and that
its body and this file are the same text. Judge the quality of that text
against the change it describes.

- The body explains what changed and why in prose a reviewer who has not seen
  the task can follow: not a list of file names, not a restatement of the task
  title.
- What it claims matches the branch: the behaviour it describes is the
  behaviour the diff implements, and it does not promise work that is absent or
  omit a user-visible change that is present.
- It says how the change was verified and names the Spock specs that cover it,
  and those specs exist under `src/test/groovy/com/example/`.
- It points at the archived OpenSpec change under `openspec/changes/archive/`,
  and that directory exists.
- It references the task's issue with `Refs #<issue>`, not `Closes`/`Fixes` —
  this project closes its own issues.
- This stage wrote nothing but `pr-body.md`: `src/`, `openspec/`,
  `build.gradle`, the Gradle wrapper and `.gnomish/` are untouched by it.

Judge by reading `pr-body.md`, the branch diff and the archived change only. Do
not run the build, the tests or `gh`: the deterministic checks have already run
before you, and their green result is a precondition of your review, not part
of it.
