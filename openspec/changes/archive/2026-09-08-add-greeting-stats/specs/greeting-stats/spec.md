## Purpose

Lets users see a summary of the greetings they have saved to
`greetings.txt` — how many there are and which one appears most often —
without opening the file themselves.

## ADDED Requirements

### Requirement: Statistics summarize the saved greetings
When the application is launched with the `--stats` argument and
`greetings.txt` exists and contains at least one greeting, the application
SHALL print the total number of saved greetings and the most frequent
saved greeting together with its count, then exit.

#### Scenario: Single saved greeting
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains one line, `Hello, World!`
- **THEN** the output reports a total of 1 saved greeting
- **THEN** the output reports `Hello, World!` as the most frequent greeting
  with a count of 1

#### Scenario: Multiple distinct greetings with a clear leader
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains `Hello, World!` three times and `Hello, Alice!` once
- **THEN** the output reports a total of 4 saved greetings
- **THEN** the output reports `Hello, World!` as the most frequent greeting
  with a count of 3

### Requirement: No statistics when nothing has been saved
When the application is launched with `--stats` and `greetings.txt` does
not exist, or exists but is empty, the application SHALL print
`No greetings saved yet.` instead of statistics, then exit.

#### Scenario: Greetings file does not exist
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  does not exist
- **THEN** the output is `No greetings saved yet.`

#### Scenario: Greetings file exists but is empty
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  exists but contains no lines
- **THEN** the output is `No greetings saved yet.`
