# Specify stage instructions

You turn the task into an OpenSpec change proposal. This stage produces
specification documents only — no production code, no tests, no build files.
The next stage implements what you write, and it sees your documents, not this
conversation: everything the implementer needs must end up in the files.

The project uses the OpenSpec CLI (`openspec`, schema `spec-driven`, project
context and rules in `openspec/config.yaml`). A change needs four artifacts, in
this order: `proposal` → `specs` → `design` → `tasks`.

Steps:

1. Pick a short kebab-case change name describing the task, e.g.
   `add-greeting-command`, and create it: `openspec new change <name>`.
2. For each artifact in order, ask the CLI what to write and follow it:
   `openspec instructions <artifact> --change <name>`, where `<artifact>` is
   `proposal`, `specs`, `design` or `tasks`. Its `<output>` block names the file
   path to write, and its `<template>` block gives the structure.
3. Track progress with `openspec status --change <name>` and finish with
   `openspec validate <name> --type change --strict` — it must pass.

Rules:

- Everything you write lives under `openspec/changes/<name>/`. Do not touch
  `src/`, `build.gradle`, the Gradle wrapper, or `.gnomish/`.
- Create exactly one change: the next stage has to find it unambiguously.
- Specs describe observable behaviour — inputs, outputs, error conditions — not
  implementation steps. The "how" belongs in `design.md`.
- `tasks.md` is the hand-off. Each task must be small, ordered and
  independently verifiable, and must name the Spock spec under
  `src/test/groovy/com/example/` that proves it.
- Scope the change to what the task asks and nothing more. If the task is a
  pure refactor, tooling or docs change with no behavioural delta, set
  `skip_specs: true` in the change's `.openspec.yaml` instead of inventing a
  requirement to satisfy validation.
- Do not implement anything. Do not commit — the factory commits your work
  itself.

Keep your output small. There is no hard ceiling — the factory drains the
round's stdout while it runs — but every printed byte is context and money. So:
pipe long command output through `tail -30`, read the specific lines you need
rather than whole files, and keep your closing summary to a few sentences.
