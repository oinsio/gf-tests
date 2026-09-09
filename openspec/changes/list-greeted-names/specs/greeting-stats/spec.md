## ADDED Requirements

### Requirement: Statistics list everyone who was greeted
When the application is launched with the `--stats` argument and
`greetings.txt` exists and contains at least one greeting, the application
SHALL, in addition to the total and most frequent greeting, print the list
of distinct names that have appeared in saved greetings, with each name
listed only once regardless of how many times it was greeted.

#### Scenario: Single saved greeting
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains one line, `Hello, World!`
- **THEN** the output lists `World` as one of the greeted names

#### Scenario: Same name greeted multiple times
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains `Hello, World!` three times and `Hello, Alice!` once
- **THEN** the output lists `World` exactly once
- **THEN** the output lists `Alice` exactly once

#### Scenario: Multiple distinct names
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains greetings to `World`, `Alice`, and `Bob`
- **THEN** the output lists `World`, `Alice`, and `Bob`, each exactly once
