## ADDED Requirements

### Requirement: The --stats argument bypasses the normal greeting flow
When the application is launched with `--stats` as an argument, it SHALL
not compute or print a greeting, not print or prompt for the save
confirmation, and not append anything to `greetings.txt`. Instead it SHALL
report greeting statistics and exit.

#### Scenario: --stats suppresses the greeting and save prompt
- **WHEN** the application is launched with the argument `--stats`
- **THEN** no greeting is printed
- **THEN** no "Save greeting to file?" prompt is printed
- **THEN** `greetings.txt` is not modified
