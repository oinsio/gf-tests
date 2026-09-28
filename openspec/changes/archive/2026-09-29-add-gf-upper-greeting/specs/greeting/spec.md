# Spec Delta

## ADDED Requirements

### Requirement: Upper-case greeting via GF_UPPER environment variable
When the `GF_UPPER` environment variable is set to exactly `1`, the
application SHALL print the greeting in upper case. Only the printed
greeting line is affected: the greeting history display and the text
appended to `greetings.txt` when the user agrees to save SHALL continue to
use the original, non-upper-cased greeting.

#### Scenario: GF_UPPER set to 1 upper-cases the printed greeting
- **WHEN** the application is launched with the argument `Alice` and the
  `GF_UPPER` environment variable set to `1`
- **THEN** the printed greeting is `HELLO, ALICE!`

#### Scenario: GF_UPPER unset leaves the greeting unchanged
- **WHEN** the application is launched with the argument `Alice` and the
  `GF_UPPER` environment variable is not set
- **THEN** the printed greeting is `Hello, Alice!`

#### Scenario: GF_UPPER set to a value other than 1 leaves the greeting unchanged
- **WHEN** the application is launched with no arguments and the
  `GF_UPPER` environment variable set to `0`
- **THEN** the printed greeting is `Hello, World!`

#### Scenario: GF_UPPER does not affect the saved greeting
- **WHEN** the application is launched with the argument `Alice`, the
  `GF_UPPER` environment variable set to `1`, and the user agrees to save
- **THEN** `Hello, Alice!` is appended to `greetings.txt`
