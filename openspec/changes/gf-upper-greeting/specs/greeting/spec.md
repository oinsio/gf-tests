## ADDED Requirements

### Requirement: Upper-case greeting via GF_UPPER
When the `GF_UPPER` environment variable is set to exactly `1`, the
application SHALL convert the greeting to upper case before it is printed,
counted as part of the greeting history, and appended to `greetings.txt`
when the user agrees to save it.

#### Scenario: GF_UPPER=1 upper-cases the default greeting
- **WHEN** the application is launched with no command-line arguments and
  `GF_UPPER` set to `1`
- **THEN** the greeting is `HELLO, WORLD!`

#### Scenario: GF_UPPER=1 upper-cases a personalized greeting
- **WHEN** the application is launched with the argument `Alice` and
  `GF_UPPER` set to `1`
- **THEN** the greeting is `HELLO, ALICE!`

#### Scenario: GF_UPPER=1 upper-cases the saved greeting
- **WHEN** the application is launched with the argument `Alice`,
  `GF_UPPER` set to `1`, and the user agrees to save
- **THEN** `HELLO, ALICE!` is appended to `greetings.txt`

#### Scenario: GF_UPPER unset leaves the greeting unchanged
- **WHEN** the application is launched with the argument `Alice` and
  `GF_UPPER` is not set
- **THEN** the greeting is `Hello, Alice!`

#### Scenario: GF_UPPER set to a value other than 1 leaves the greeting unchanged
- **WHEN** the application is launched with the argument `Alice` and
  `GF_UPPER` set to `true`
- **THEN** the greeting is `Hello, Alice!`
