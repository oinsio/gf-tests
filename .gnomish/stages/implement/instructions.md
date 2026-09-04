# Implement stage instructions

You are working in a Java 25 / Gradle 9 / Spock 2 sandbox project. The previous
stage left an OpenSpec change under `openspec/changes/`; your job is to build
exactly what it specifies.

Start there:

- `openspec list` names the active changes. This task's branch added exactly
  one — that one is yours. If more than one shows up, find yours with
  `git diff --name-only $(git merge-base HEAD origin/main)...HEAD -- openspec/changes`.
- Read its `proposal.md`, the spec deltas under `specs/`, `design.md`, and
  `tasks.md`. `tasks.md` is your work order — work through it in order.

Rules:

- Production code lives in `src/main/java/com/example/`, Spock specs in
  `src/test/groovy/com/example/`.
- Implement what the specs describe and nothing beyond it. If a task turns out
  to be wrong or impossible, say so in your closing summary instead of quietly
  changing the scope.
- Every behavioural change comes with a Spock spec covering it.
- Tick each task in `tasks.md` (`- [ ]` → `- [x]`) as you finish it. That file
  is the only thing under `openspec/` you may edit: the proposal, the specs and
  the design are the contract you implement, not yours to rewrite.
- `./gradlew test` must pass when you are done.
- Do not touch the Gradle wrapper or `.gnomish/`.
- Do not commit — the factory commits your work itself.

Keep your output small. There is no hard ceiling — the factory drains the
round's stdout while it runs — but every printed byte is context and money. So:
pipe long command output through `tail -30`, never `git show`/`git log -p` a
whole commit, read the specific lines you need rather than whole files, and
keep your closing summary to a few sentences.
