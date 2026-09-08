## MODIFIED Requirements

### Requirement: Greeting drives history and save behavior
The greeting computed from the command-line arguments, not a fixed
`Hello, World!` string, SHALL be the greeting that is printed, counted as
part of the greeting history, and, prefixed with the current save
timestamp, appended to `greetings.txt` when the user agrees to save it.

#### Scenario: Personalized greeting is offered for saving
- **WHEN** the application is launched with the argument `Alice` and the
  user agrees to save
- **THEN** a line ending in `Hello, Alice!` and starting with the current
  save timestamp is appended to `greetings.txt`

#### Scenario: Personalized greeting counts in the printed history
- **WHEN** the application is launched with the argument `Alice` and
  `greetings.txt` already contains one prior greeting
- **THEN** the printed history includes `Hello, Alice!` and reports it as
  greeting number 2
