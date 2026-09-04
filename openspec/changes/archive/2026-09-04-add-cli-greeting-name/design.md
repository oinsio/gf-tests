## Context

`HelloWorld.main` currently hardcodes `GREETING = "Hello, World!"` and passes
it straight into `run(in, out, outputFile)`, which prints it, prints the
history read via `readHistory`, and appends it via `appendGreeting` if the
user agrees. `run` never sees `args`; `main` calls
`run(System.in, System.out, GREETINGS_FILE)` with no name-derived value. See
`proposal.md` for motivation and `specs/greeting/spec.md` for the exact
behavioral requirements.

## Goals / Non-Goals

**Goals:**
- Compute the greeting from `args` in `main` and thread it through the
  existing `run` flow so history, counting and saving all use it.
- Keep `run`'s signature-level responsibilities (printing, history,
  save-prompt) unchanged in shape, just parameterized by greeting text.

**Non-Goals:**
- No new CLI flags/parsing library (e.g. no `--name=`); a bare positional
  argument list is sufficient per the proposal.
- No locale-aware name formatting (capitalization, transliteration, etc.).
- No changes to `greetings.txt` file format or the save-confirmation prompt.

## Decisions

- **Where args are turned into a greeting**: a small static helper,
  `greetingFor(String[] args)`, added next to `GREETING`. It joins `args`
  with `" "`, trims, and returns `"Hello, " + name + "!"`, or falls back to
  `GREETING` when the trimmed name is empty.
- **`run`'s signature gains a `greeting` parameter**: `run` currently reads
  the module-level `GREETING` constant directly. It changes to accept the
  greeting text as its first parameter (`run(String greeting, InputStream
  in, PrintStream out, Path outputFile)`) and use that instead of the
  constant everywhere it currently prints/counts/saves `GREETING`. `main`
  calls `run(greetingFor(args), System.in, System.out, GREETINGS_FILE)`.
  Alternative considered: keep `run`'s signature untouched and have it
  re-derive the greeting from `System.getProperty`/a shared mutable field.
  Rejected as hidden global state that's harder to test and out of step with
  `run` already being test-driven via explicit parameters (`in`, `out`,
  `outputFile`).
  Existing direct callers of `run` in tests must be updated to pass the
  greeting explicitly (e.g. `"Hello, World!"`) — this is a mechanical,
  same-behavior update, not a behavior change for those scenarios.
- **Join strategy for multiple arguments**: join with a single space
  (`String.join(" ", args)`). This mirrors how a shell would normally present
  `args` as separate words of one name and needs no extra dependency.
- **Blank-argument fallback**: after joining, `String::trim` and compare to
  empty; empty means "no usable name", so fall back to the existing constant
  rather than printing `Hello, !`.

## Risks / Trade-offs

- [Ambiguous multi-argument input, e.g. `--args="Alice Bob"` vs
  `--args="Alice" --args="Bob"` both look the same to `String.join`] →
  Acceptable per spec: both produce `Hello, Alice Bob!`; this is the defined,
  documented behavior, not an accidental gap.
- [`run`'s signature change breaks existing direct-call test scenarios] →
  Mitigated by updating those call sites to pass `"Hello, World!"` explicitly;
  the assertions in those scenarios stay the same since that's still the
  greeting under test.
