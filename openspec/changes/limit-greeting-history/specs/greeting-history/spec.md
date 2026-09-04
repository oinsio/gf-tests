## Purpose

Defines how the greeting history section printed on each run is limited to a
recent window and how omitted older greetings are summarized for the user.

## ADDED Requirements

### Requirement: Display at most 5 most recent greetings
The system SHALL print at most the 5 most recent greetings from the history
file in the "История приветствий:" section, in their original chronological
order (oldest of the shown greetings first, newest last).

#### Scenario: History has 5 or fewer greetings
- **WHEN** the greetings file contains 5 or fewer lines
- **THEN** the system prints every line from the file, in file order, with no
  summary line

#### Scenario: History has more than 5 greetings
- **WHEN** the greetings file contains more than 5 lines
- **THEN** the system prints only the last 5 lines from the file, in their
  original chronological order

### Requirement: Summarize omitted older greetings
When the history file contains more than 5 lines, the system SHALL print a
summary line, before the shown greetings, of the exact form `и ещё N ранее`,
where N is the number of lines omitted (total lines in the file minus 5).

#### Scenario: Summary count matches omitted lines
- **WHEN** the greetings file contains 8 lines
- **THEN** the system prints the line `и ещё 3 ранее` before the 5 most
  recent greetings

#### Scenario: No summary line when nothing is omitted
- **WHEN** the greetings file contains 5 or fewer lines
- **THEN** the system does not print any "и ещё N ранее" line

### Requirement: Greeting number reflects true total count
The system SHALL continue to report the total number of greetings (existing
history size plus the current greeting) in the "Это приветствие номер N."
line, regardless of how many history lines are actually displayed.

#### Scenario: Greeting number unaffected by display limit
- **WHEN** the greetings file contains 8 lines
- **THEN** the system prints "Это приветствие номер 9." after the history
  section, even though only 5 history lines and a summary line were shown
