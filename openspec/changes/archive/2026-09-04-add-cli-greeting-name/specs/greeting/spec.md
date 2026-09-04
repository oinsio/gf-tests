## Purpose

Defines the greeting text the application prints, counts in its printed
history, and offers to save to `greetings.txt`, based on the command-line
arguments the process was launched with.

## ADDED Requirements

### Requirement: Default greeting with no arguments
When the application is launched with no command-line arguments, it SHALL
use `Hello, World!` as the greeting.

#### Scenario: No arguments supplied
- **WHEN** the application is launched with an empty argument list
- **THEN** the greeting is `Hello, World!`

### Requirement: Personalized greeting from a single argument
When the application is launched with a single non-blank command-line
argument, it SHALL use that argument, trimmed of leading and trailing
whitespace, as the name in the greeting `Hello, <name>!`.

#### Scenario: Single name argument
- **WHEN** the application is launched with the argument `Alice`
- **THEN** the greeting is `Hello, Alice!`

#### Scenario: Argument with surrounding whitespace
- **WHEN** the application is launched with the argument `  Alice  `
- **THEN** the greeting is `Hello, Alice!`

### Requirement: Personalized greeting from multiple arguments
When the application is launched with more than one command-line argument,
it SHALL join all arguments with a single space, trim the result, and use
it as the name in the greeting `Hello, <name>!`.

#### Scenario: Multiple arguments form a full name
- **WHEN** the application is launched with the arguments `Alice` and `Smith`
- **THEN** the greeting is `Hello, Alice Smith!`

### Requirement: Fallback to default greeting on blank arguments
When every command-line argument is empty or consists only of whitespace,
so the joined and trimmed name is an empty string, the application SHALL
fall back to the default greeting `Hello, World!`.

#### Scenario: Single blank argument
- **WHEN** the application is launched with a single argument that is an
  empty string
- **THEN** the greeting is `Hello, World!`

#### Scenario: Single whitespace-only argument
- **WHEN** the application is launched with a single argument consisting
  only of spaces
- **THEN** the greeting is `Hello, World!`

### Requirement: Greeting drives history and save behavior
The greeting computed from the command-line arguments, not a fixed
`Hello, World!` string, SHALL be the greeting that is printed, counted as
part of the greeting history, and appended to `greetings.txt` when the user
agrees to save it.

#### Scenario: Personalized greeting is offered for saving
- **WHEN** the application is launched with the argument `Alice` and the
  user agrees to save
- **THEN** `Hello, Alice!` is appended to `greetings.txt`

#### Scenario: Personalized greeting counts in the printed history
- **WHEN** the application is launched with the argument `Alice` and
  `greetings.txt` already contains one prior greeting
- **THEN** the printed history includes `Hello, Alice!` and reports it as
  greeting number 2
