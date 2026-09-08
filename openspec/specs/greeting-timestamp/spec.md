# greeting-timestamp Specification

## Purpose
Defines the timestamp prefix recorded with each newly saved greeting, the
injectable clock that produces it, and how both timestamped and legacy
untimestamped history lines are read back and displayed.

## Requirements

### Requirement: Timestamp prefix on save
When a greeting is saved to the history file, the system SHALL prefix it
with the current local date and time, formatted as `yyyy-MM-dd HH:mm`,
followed by a single space, before the greeting text.

#### Scenario: Saving a greeting records the current timestamp
- **WHEN** a greeting is saved at local time 2026-09-07 14:30
- **THEN** the line appended to the history file is
  `2026-09-07 14:30 Hello, World!`

### Requirement: Injectable clock
The source of the current time used to produce the save timestamp SHALL be
injectable, so a fixed, caller-supplied clock can be used in place of the
system clock.

#### Scenario: A fixed clock produces a deterministic timestamp
- **WHEN** a greeting is saved using a caller-supplied clock fixed at
  2026-09-07 14:30
- **THEN** the line appended to the history file starts with
  `2026-09-07 14:30 `

### Requirement: Backward-compatible reading of history lines
When reading the history file for display, the system SHALL recognize a
leading `yyyy-MM-dd HH:mm ` timestamp on a line and separate it from the
greeting text. Lines that do not start with a timestamp in that format
SHALL be read and displayed unchanged, with no timestamp.

#### Scenario: Reading a timestamped line
- **WHEN** the history file contains the line
  `2026-09-07 14:30 Hello, World!`
- **THEN** the system reads it as the timestamp `2026-09-07 14:30` and the
  greeting `Hello, World!`

#### Scenario: Reading a legacy line without a timestamp
- **WHEN** the history file contains the line `Hello, World!` with no
  timestamp prefix
- **THEN** the system reads it as the greeting `Hello, World!` with no
  timestamp

### Requirement: Timestamp shown in printed history
When the printed history includes a line that has a recorded timestamp, the
system SHALL display that timestamp before the greeting text, in the same
`yyyy-MM-dd HH:mm ` format it was stored in. A line with no recorded
timestamp SHALL be displayed as just the greeting text.

#### Scenario: Displaying a timestamped history entry
- **WHEN** the printed history includes an entry saved at 2026-09-07 14:30
  with greeting `Hello, World!`
- **THEN** the system prints `2026-09-07 14:30 Hello, World!` for that entry

#### Scenario: Displaying a legacy history entry
- **WHEN** the printed history includes a legacy entry with greeting
  `Hello, World!` and no recorded timestamp
- **THEN** the system prints `Hello, World!` for that entry, with no
  timestamp
