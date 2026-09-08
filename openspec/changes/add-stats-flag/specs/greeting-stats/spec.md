## Purpose

Defines the `--stats` command-line flag, which reports summary statistics
about the greetings previously saved to `greetings.txt` instead of running
the normal greet-and-save flow.

## ADDED Requirements

### Requirement: Dispatch to statistics mode on --stats
When the application is launched with `--stats` as an argument, it SHALL
print greeting statistics and exit without printing a greeting, without
printing greeting history, and without prompting to save a greeting.

#### Scenario: --stats is supplied
- **WHEN** the application is launched with the argument `--stats`
- **THEN** the application does not print a greeting
- **AND** the application does not prompt to save a greeting

### Requirement: Report total count and most frequent greeting
When `--stats` is supplied and `greetings.txt` exists and contains at least
one saved greeting, the application SHALL print the total number of saved
greetings and the most frequent greeting together with its count.

#### Scenario: Multiple saved greetings with a clear most-frequent entry
- **WHEN** the application is launched with `--stats`
- **AND** `greetings.txt` contains the lines `Hello, World!`, `Hello, Alice!`,
  `Hello, World!`
- **THEN** the application reports a total of 3 saved greetings
- **AND** the application reports `Hello, World!` as the most frequent
  greeting with a count of 2

#### Scenario: All saved greetings are equally frequent
- **WHEN** the application is launched with `--stats`
- **AND** `greetings.txt` contains distinct greetings that each appear
  exactly once
- **THEN** the application reports the total count of saved greetings
- **AND** the application reports one of those greetings as the most
  frequent, with a count of 1

### Requirement: Report absence of saved greetings
When `--stats` is supplied and `greetings.txt` does not exist, or exists but
contains no saved greetings, the application SHALL print
`No greetings saved yet.` and SHALL NOT print a total count or a most
frequent greeting.

#### Scenario: Greetings file does not exist
- **WHEN** the application is launched with `--stats`
- **AND** `greetings.txt` does not exist
- **THEN** the application prints `No greetings saved yet.`

#### Scenario: Greetings file exists but is empty
- **WHEN** the application is launched with `--stats`
- **AND** `greetings.txt` exists and is empty
- **THEN** the application prints `No greetings saved yet.`
