# greeting-timestamp Specification

## Purpose
Defines the timestamp prefix written when a greeting is saved, how it is
displayed in the startup history alongside older untimestamped lines, and
the requirement that the time source be injectable for exact testing.

## Requirements

### Requirement: Save time is prefixed onto a saved greeting
When a greeting is appended to `greetings.txt`, the system SHALL prefix it
with the current save time formatted as `yyyy-MM-dd HH:mm`, followed by a
single space, before the greeting text.

#### Scenario: Saving a greeting records the current time
- **WHEN** the current time is 2026-09-07 14:30 and the user agrees to save
  the greeting `Hello, World!`
- **THEN** the line `2026-09-07 14:30 Hello, World!` is appended to
  `greetings.txt`

#### Scenario: Consecutive saves each get their own timestamp
- **WHEN** a greeting is saved at 2026-09-07 14:30 and another greeting is
  saved at 2026-09-07 14:31
- **THEN** `greetings.txt` contains one line prefixed `2026-09-07 14:30`
  and one line prefixed `2026-09-07 14:31`

### Requirement: History display shows the timestamp before the greeting
When printing a history line that has a save-time prefix, the system SHALL
display that timestamp before the greeting text, in the same
`yyyy-MM-dd HH:mm` format it was stored in.

#### Scenario: Timestamped line is displayed with its timestamp
- **WHEN** `greetings.txt` contains the line
  `2026-09-07 14:30 Hello, World!`
- **THEN** the startup history displays `2026-09-07 14:30 Hello, World!`

### Requirement: Lines saved without a timestamp remain readable
A line already stored in `greetings.txt` in the format that existed before
this change (the greeting text with no save-time prefix) SHALL be read and
displayed exactly as stored, with no timestamp added and no error raised.

#### Scenario: Legacy line is displayed unchanged
- **WHEN** `greetings.txt` contains the line `Hello, World!` with no
  timestamp prefix
- **THEN** the startup history displays `Hello, World!` unchanged

#### Scenario: Mixed legacy and timestamped history
- **WHEN** `greetings.txt` contains the legacy line `Hello, World!`
  followed by the timestamped line `2026-09-07 14:30 Hello, Alice!`
- **THEN** the startup history displays `Hello, World!` unchanged and
  `2026-09-07 14:30 Hello, Alice!` with its timestamp

### Requirement: Save time source is injectable
The source of the current time used to stamp a saved greeting SHALL be an
injectable dependency rather than a fixed, non-overridable call to the
system clock, so that an exact save time can be supplied when verifying
behavior.

#### Scenario: An exact save time is supplied
- **WHEN** the greeting-saving operation is invoked with a time source
  fixed to 2026-09-07 14:30 and the user agrees to save
- **THEN** the saved line is prefixed with exactly `2026-09-07 14:30`
