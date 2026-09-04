# Archive stage instructions

The change this task proposed is now implemented and the build is green. Your
job is the bookkeeping step: retire the change and promote what it specified
into the project's living specs under `openspec/specs/`.

Do it with the OpenSpec CLI — never by moving files yourself:

- `openspec list` names the active changes. This task's branch added exactly
  one — that one is yours. If more than one shows up, find yours with
  `git diff --name-only $(git merge-base HEAD origin/main)...HEAD -- openspec/changes`.
- Check it really is finished: `openspec status --change <name>` and no
  remaining `- [ ]` in its `tasks.md`. If a task is still open, stop and say so
  in your closing summary — do not tick it yourself, and do not archive.
- Archive it: `openspec archive <name> --yes --json`. This moves the change to
  `openspec/changes/archive/<date>-<name>/` and merges its spec deltas into
  `openspec/specs/`. A change marked `skip_specs: true` is handled
  automatically — no extra flag.
- Confirm: `openspec validate --specs --strict --no-interactive` and
  `openspec validate --archived --no-interactive` both pass, and
  `openspec/changes/` holds nothing but `archive/`.

Rules:

- Archive only this task's change. Any other active change belongs to someone
  else — leave it alone.
- Do not hand-edit anything under `openspec/specs/` or inside
  `openspec/changes/archive/`. The CLI writes those; your job is to run it. The
  one exception: if `openspec validate --specs --strict` rejects a generated
  spec file, fix that file so it validates, and say what you changed and why.
- Touch no source code. `src/`, `build.gradle` and the Gradle wrapper are done
  and must stay exactly as the implement stage left them.
- Do not touch `.gnomish/`.
- Do not commit — the factory commits your work itself.

Keep your output small. There is no hard ceiling — the factory drains the
round's stdout while it runs — but every printed byte is context and money. So:
pipe long command output through `tail -30`, read the specific lines you need
rather than whole files, and keep your closing summary to a few sentences.
