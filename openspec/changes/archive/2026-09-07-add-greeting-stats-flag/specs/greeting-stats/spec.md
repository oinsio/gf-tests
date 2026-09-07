## Purpose

Lets a user quickly see aggregate information about the greetings that have been saved to disk, without printing or saving a new greeting.

## ADDED Requirements

### Requirement: Statistics command dispatch
The system SHALL recognize a `--stats` command-line argument. When `--stats` is present among the program arguments, the system SHALL print only greeting statistics and then exit: it SHALL NOT print a greeting, SHALL NOT print greeting history, and SHALL NOT prompt whether to save a greeting.

#### Scenario: Stats flag suppresses normal greeting flow
- **WHEN** the application is started with `--stats` as an argument
- **THEN** the output does not contain a greeting line, does not contain "Greeting history:", and does not contain "Save greeting to file?"

### Requirement: Total and most frequent greeting statistics
When `greetings.txt` exists and contains at least one saved greeting, the system SHALL print the total number of saved greetings and the most frequent greeting together with its occurrence count.

#### Scenario: Statistics for a file with a single most-frequent greeting
- **GIVEN** `greetings.txt` contains "Hello, World!" three times and "Hello, Alice!" once
- **WHEN** the application is started with `--stats`
- **THEN** the output reports a total of 4 saved greetings
- **AND** the output reports "Hello, World!" as the most frequent greeting with a count of 3

#### Scenario: Statistics when all saved greetings are identical
- **GIVEN** `greetings.txt` contains "Hello, World!" twice and no other greetings
- **WHEN** the application is started with `--stats`
- **THEN** the output reports a total of 2 saved greetings
- **AND** the output reports "Hello, World!" as the most frequent greeting with a count of 2

### Requirement: No saved greetings
If `greetings.txt` does not exist, or exists but contains no saved greetings, the system SHALL print exactly the message `No greetings saved yet.` and SHALL NOT print a total count or a most-frequent greeting.

#### Scenario: Stats requested with no greetings file
- **GIVEN** `greetings.txt` does not exist
- **WHEN** the application is started with `--stats`
- **THEN** the output contains "No greetings saved yet."

#### Scenario: Stats requested with an empty greetings file
- **GIVEN** `greetings.txt` exists and is empty
- **WHEN** the application is started with `--stats`
- **THEN** the output contains "No greetings saved yet."
