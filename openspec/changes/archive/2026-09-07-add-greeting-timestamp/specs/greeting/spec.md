## MODIFIED Requirements

### Requirement: Greeting drives history and save behavior
The greeting computed from the command-line arguments, not a fixed
`Hello, World!` string, SHALL be the greeting that is printed, counted as
part of the greeting history, and appended to `greetings.txt`, prefixed
with the save timestamp, when the user agrees to save it.

#### Scenario: Personalized greeting is offered for saving
- **WHEN** the application is launched with the argument `Alice` and the
  user agrees to save
- **THEN** a line consisting of the save timestamp, a single space, and
  `Hello, Alice!` is appended to `greetings.txt`

#### Scenario: Personalized greeting counts in the printed history
- **WHEN** the application is launched with the argument `Alice` and
  `greetings.txt` already contains one prior greeting
- **THEN** the printed history includes `Hello, Alice!` and reports it as
  greeting number 2

## ADDED Requirements

### Requirement: Saved greeting is timestamped
When a greeting is saved, the system SHALL prefix the line written to
`greetings.txt` with the save time, formatted `yyyy-MM-dd HH:mm`, followed
by a single space, ahead of the greeting text.

#### Scenario: Saved line carries a timestamp
- **WHEN** the application saves the greeting `Hello, World!` at
  `2026-09-07 14:30`
- **THEN** the line appended to `greetings.txt` is
  `2026-09-07 14:30 Hello, World!`
