# greeting-history-display Specification

## Purpose
Controls how many previously saved greetings are printed on each run, so the
console output stays short even as `greetings.txt` accumulates many entries.

## Requirements

### Requirement: Display only the most recent greetings
The system SHALL print at most the 5 most recent entries from the greeting
history, in reverse chronological order (most recent entry first, oldest of
the shown entries last).

#### Scenario: History has more than 5 entries
- **WHEN** the greeting history file contains more than 5 lines
- **THEN** the system prints only the last 5 lines of the history, most
  recent first

#### Scenario: History has 5 or fewer entries
- **WHEN** the greeting history file contains 5 or fewer lines
- **THEN** the system prints all of the history lines, most recent first

### Requirement: Report the count of hidden older greetings
When entries are omitted from the printed history, the system SHALL print a
summary line stating how many older greetings were not shown, in the exact
form `и ещё N ранее.` where N is the number of hidden entries. This line is
printed after the displayed history lines, since those hidden entries are
older than everything just shown. This line is omitted entirely when no
entries are hidden.

#### Scenario: More than 5 greetings exist
- **WHEN** the greeting history file contains 8 lines
- **THEN** the system prints the 5 displayed history lines, most recent
  first, followed by the line `и ещё 3 ранее.`

#### Scenario: Exactly 5 greetings exist
- **WHEN** the greeting history file contains exactly 5 lines
- **THEN** the system prints all 5 lines, most recent first, and does not
  print an `и ещё ... ранее.` line

#### Scenario: Fewer than 5 greetings exist
- **WHEN** the greeting history file contains 2 lines
- **THEN** the system prints both lines, most recent first, and does not
  print an `и ещё ... ранее.` line

#### Scenario: History is empty
- **WHEN** the greeting history file does not exist or is empty
- **THEN** the system prints no history lines and does not print an
  `и ещё ... ранее.` line

### Requirement: Greeting number counts the full history
The greeting number reported after the history (`Это приветствие номер N.`)
SHALL reflect the total number of greetings in the history plus the current
one, regardless of how many history lines were actually printed.

#### Scenario: Greeting number with a truncated history
- **WHEN** the greeting history file contains 8 lines and a new greeting is
  being made
- **THEN** the system reports `Это приветствие номер 9.` even though only 5
  history lines and the "и ещё" summary were printed
