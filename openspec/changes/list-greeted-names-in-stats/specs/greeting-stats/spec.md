## ADDED Requirements

### Requirement: Statistics list everyone who has been greeted
When the application is launched with the `--stats` argument and
`greetings.txt` exists and contains at least one greeting, the application
SHALL, in addition to the total count and most frequent greeting, print
the list of unique names that have been greeted. A name is the text
between `Hello, ` and the trailing `!` in a saved greeting. Each name
SHALL appear only once in this list, regardless of how many times it was
greeted, ordered by the position of its first occurrence in
`greetings.txt`. The list SHALL be printed as `Greeted: ` followed by the
names in that order, separated by `, `.

#### Scenario: Single saved greeting
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains one line, `Hello, World!`
- **THEN** the output includes `Greeted: World`

#### Scenario: Same name greeted more than once is listed only once
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains `Hello, World!` three times and `Hello, Alice!` once, in that
  order
- **THEN** the output includes `Greeted: World, Alice`

#### Scenario: Multiple distinct names keep first-occurrence order
- **WHEN** the application is launched with `--stats` and `greetings.txt`
  contains, in order, `Hello, Alice!`, `Hello, Bob!`, `Hello, Alice!`,
  `Hello, Carol!`
- **THEN** the output includes `Greeted: Alice, Bob, Carol`
