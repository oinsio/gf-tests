# Gnomish Factory tests

Sandbox project for testing Java/Gradle/Spock setup for https://github.com/oinsio/gnomish-factory.

## Technologies

- Java 25
- Gradle 9.7
- Spock Framework 2.4 + Groovy 4.0
- OpenSpec (spec-driven development)

## Run application

```bash
./gradlew run
```

Prints `Hello, World!`.

## Features

- Prints the `Hello, World!` greeting.
- Shows the history of previously saved greetings on each run.
- Reports the sequence number of the current greeting (e.g. "This is greeting number 3.").
- Prompts the user whether to save the greeting, accepting `yes`/`y`/`да` as confirmation.
- Appends confirmed greetings to `greetings.txt`, preserving prior entries.
- `HelloWorld#greet(name)` builds a personalized greeting message.

## Factory credentials

The factory reads its GitHub token through its file indirection: for a secret `N` it takes
`N_FILE` as a path and reads the value out of that file, so the token never sits in an
environment table. `./gnomish` points those variables at local files — kept outside the clone
(with `binding=host` the gnome works inside this tree) and per project, keyed by the clone's
directory name the same way the factory keys `~/.gnomish/worktrees/<project>`:

```bash
mkdir -p ~/.gnomish/secrets/gf-tests
install -m 600 /dev/null ~/.gnomish/secrets/gf-tests/github-token
# paste the token into the file — the whole file is the value, no KEY= prefix, no quotes
```

| File                                              | Secret                          | Needed for                                                 |
|---------------------------------------------------|---------------------------------|-------------------------------------------------------------|
| `~/.gnomish/secrets/gf-tests/github-token`         | `GNOMISH_GITHUB_TOKEN`          | the tracker: issue read/write + label write                 |
| `~/.gnomish/secrets/gf-tests/github-actions-token` | `GNOMISH_GITHUB_ACTIONS_TOKEN`  | a stage's GitHub Actions check (`actions: read`); optional  |
| `~/.gnomish/secrets/gf-tests/github-pr-token`      | `GH_TOKEN`                      | the `deliver` stage: `gh pr create`/`gh pr edit`             |

The wrapper only points at a file that exists, so exporting `GNOMISH_GITHUB_TOKEN` in the
shell still works. `GNOMISH_SECRETS_DIR` moves the directory, `GNOMISH_GITHUB_TOKEN_FILE`
names one file directly. A referenced-but-unreadable file resolves the secret as absent and
never falls back to the plain variable — the provider is deliberately fail-closed.

## Pipeline

`.gnomish/pipeline.yaml` runs four stages per task; each one's manifest, instructions and
acceptance criteria live in `.gnomish/stages/<stage>/`:

| Stage       | What it hands to the next one                                              |
|-------------|----------------------------------------------------------------------------|
| `specify`   | one validated OpenSpec change under `openspec/changes/`                      |
| `implement` | the code and Spock specs it calls for, with `./gradlew test` green           |
| `archive`   | that change archived, its spec deltas folded into `openspec/specs/`          |
| `deliver`   | the pull request for the task branch, open against `main`                    |

`deliver` writes no project files. It reads the branch, the repo from
`tracker.github.repo` and the issue number from `.gnomish-task/task.json`, then opens the
pull request — or edits the one already there, so a retry never opens a second. The body it
publishes is also left in `pr-body.md` (git-ignored): that file is what the stage's judge
reads, and a command check fails the stage unless it matches what GitHub actually shows. The
body references the task with `Refs #N`, never `Closes` — closing the issue stays yours.

Both the gnome's round and that stage's checks call `gh`, which reads `GH_TOKEN`. The tracker's
own `GNOMISH_GITHUB_TOKEN` cannot serve: the factory declares it a credential, so it is scrubbed
from every child environment and refused in the passthrough allowlist. `./gnomish` therefore
exports `GH_TOKEN` from `github-pr-token`, falling back to `github-token`, and passes the name
through with `--factory.sandbox.env-passthrough=GH_TOKEN`. On that fallback the gnome holds a
token with the tracker's rights — it can write issues and labels as you; a fine-grained token
scoped to Contents + Pull requests in `github-pr-token` keeps it to what the stage needs.

Pipeline law freezes at the base of a task's branch, so `deliver` applies to tasks branched
after this change reaches `main` — anything already in flight finishes on the three-stage
pipeline it started with.

## Run the factory

```bash
./gnomish-up
```

Starts three things at once and stops them all on Ctrl-C:

- the dashboard renderer in the background — `logs/dashboard.html`, re-rendered every
  10 s, opened in a browser once;
- a follower on the factory log, `~/.gnomish/logs/gf-tests/gnomish.log`, streamed into the
  same terminal. The daemon's own console carries only `WARN` and above, so this is where
  the INFO narrative of a healthy run comes from; those two levels are filtered out of the
  follower to keep them from appearing twice;
- `gnomish serve` in the foreground.

Flags are passed through to `gnomish serve` (`./gnomish-up --slots=4 --drain`); `--no-open`
skips the browser and `--no-logs` the log follower.

`--demo` is for a screen recording. It keeps absolute paths off the screen twice over: in
the terminal this clone's path is rewritten to `.` and the rest of the home directory to
`~`, and the dashboard is served over http, so the address bar reads `http://localhost:8000`
instead of a `file:///Users/...` path. `--port=5500` moves the port; without it a busy port
is stepped over, so a second factory can record alongside the first. The server is python3's,
bound to loopback, and it stops with everything else on Ctrl-C. The log file on disk keeps
its absolute paths either way, so debugging after the recording is unaffected.

It also compacts every log line for a terminal a quarter of a screen wide. The ~130 columns of
prefix a line arrives with — date, thread, level, four MDC field names, an abbreviated logger —
become the clock and the context that actually changes, `10:47:41 [#34 specify/0] round
started: ...`; the tracker id shrinks to its issue number, `claude-sonnet-5` to `sonnet`, a
`TokenUsage[...]` record to `40→5293`; Spring's boot lines, the plugin inventory and stack-trace
frames are dropped, the exception line above them kept. Only recognisable log lines are touched,
so an unfamiliar line prints as it arrived. `--no-compact` turns this off and keeps the path
rewriting; the file on disk is verbatim either way.

The pieces are still available separately:

```bash
./gnomish serve --slots=1
./gnomish dashboard --watch --out=logs/dashboard.html
tail -F ~/.gnomish/logs/gf-tests/gnomish.log
```

Everything a running factory owns outside the clone is keyed by this clone's name, the way
`~/.gnomish/secrets/gf-tests` is: the log at `~/.gnomish/logs/gf-tests/gnomish.log`, and the
daemon's own state — `snapshot.json` plus the daily ledgers — under
`~/.gnomish/serve/gf-tests/`, via `--factory.instance-name`. Both default to one location per
host, so two factories started from two clones would otherwise overwrite each other's
snapshot (both dashboards then show whichever daemon wrote last) and interleave their logs
and ledgers. Passing your own `GNOMISH_LOG_DIR` or `--factory.instance-name` overrides it.

The instance name is also the first half of the id in the gnome's claim comments on the
tracker — `gf-tests-a1b2c3`, where the suffix is minted fresh on every start.

## Run tests

```bash
./gradlew test
```

## Project structure

```
src/
  main/java/com/example/   — source code
  test/groovy/com/example/ — Spock specs
openspec/                  — specs and workflow configuration
```
