## ADDED Requirements

### Requirement: Displayed history shows the save timestamp
When a history line is stored with a `yyyy-MM-dd HH:mm ` timestamp prefix,
the system SHALL display that line with its timestamp prefix intact.

#### Scenario: Displayed line includes its timestamp
- **WHEN** `greetings.txt` contains the line
  `2026-09-07 14:30 Hello, World!`
- **THEN** the printed history shows `2026-09-07 14:30 Hello, World!`

### Requirement: Untimestamped history lines remain readable
When a history line was stored before timestamps were introduced and has
no `yyyy-MM-dd HH:mm ` prefix, the system SHALL read and display that line
unchanged, without a timestamp.

#### Scenario: Old-format line displays without a timestamp
- **WHEN** `greetings.txt` contains the line `Hello, World!` with no
  timestamp prefix
- **THEN** the printed history shows `Hello, World!` unchanged

#### Scenario: Mixed old and new format history
- **WHEN** `greetings.txt` contains one line without a timestamp followed
  by one line with a timestamp
- **THEN** the printed history shows each line exactly as stored, one
  without a timestamp and one with it
