## ADDED Requirements

### Requirement: Statistics list every distinct greeted name
When the application is launched with `--stats` and `greetings.txt` exists
and contains at least one greeting, the application SHALL also print the
list of every distinct name that has been greeted, in the exact form
`Greeted names: <name1>, <name2>, ...`, with names ordered by their first
appearance in `greetings.txt` and each distinct name listed exactly once,
regardless of how many times it was greeted.

#### Scenario: Single saved greeting names one person
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains one line, `Hello, World!`
- **THEN** the output includes `Greeted names: World`

#### Scenario: Same name greeted more than once is listed only once
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains `Hello, World!` three times and `Hello, Alice!` once
- **THEN** the output includes `Greeted names: World, Alice`

#### Scenario: Distinct names are listed in order of first appearance
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains, in this order, `Hello, Alice!`, `Hello, Bob!`, `Hello, Alice!`
- **THEN** the output includes `Greeted names: Alice, Bob`

#### Scenario: Timestamped greetings are still resolved to their name
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains the line `2026-09-07 14:30 Hello, Alice!`
- **THEN** the output includes `Greeted names: Alice`
