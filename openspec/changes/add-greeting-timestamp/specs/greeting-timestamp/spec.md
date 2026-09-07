## Purpose

Attaches a save timestamp to each greeting written to the history file, and
governs how timestamped and legacy untimestamped lines are parsed and shown.

## ADDED Requirements

### Requirement: Timestamp prefix on save
When a greeting is saved to the history file, the system SHALL prefix it
with the current time formatted as `yyyy-MM-dd HH:mm`, followed by a single
space, followed by the greeting text.

#### Scenario: Saving a greeting records its timestamp
- **WHEN** the greeting `Hello, World!` is saved at `2026-09-07 14:30`
- **THEN** the line appended to the history file is
  `2026-09-07 14:30 Hello, World!`

### Requirement: Injectable clock
The source of the current time used to produce the save timestamp SHALL be
injectable, so a test can supply a fixed clock and assert an exact
timestamp instead of depending on the system clock.

#### Scenario: Test supplies a fixed clock
- **WHEN** a greeting is saved using a supplied clock fixed at
  `2026-09-07 14:30`
- **THEN** the saved line is prefixed with `2026-09-07 14:30`, regardless of
  the actual system time

### Requirement: Display timestamp with each history entry
When printing a history line that has a timestamp prefix, the system SHALL
display that timestamp before the greeting text, in the same
`yyyy-MM-dd HH:mm` format it was stored in.

#### Scenario: Printing a timestamped history entry
- **WHEN** the history file contains `2026-09-07 14:30 Hello, World!`
- **THEN** the printed history line shows `2026-09-07 14:30 Hello, World!`

### Requirement: Backward-compatible reading of untimestamped entries
A history line stored without a timestamp prefix (the format used before
this capability existed) SHALL still be read successfully and displayed as
plain greeting text, without a timestamp.

#### Scenario: Printing a legacy history entry with no timestamp
- **WHEN** the history file contains the line `Hello, World!` with no
  timestamp prefix
- **THEN** the printed history line shows `Hello, World!` unchanged

#### Scenario: Mixed history with and without timestamps
- **WHEN** the history file contains both `Hello, World!` (no timestamp)
  and `2026-09-07 14:30 Hello, Alice!` (timestamped)
- **THEN** the first line is displayed as `Hello, World!` and the second as
  `2026-09-07 14:30 Hello, Alice!`
