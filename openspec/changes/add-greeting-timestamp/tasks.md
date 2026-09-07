## 1. Inject a clock and timestamp saved greetings

- [ ] 1.1 Thread a `java.time.Clock` parameter through `HelloWorld.run` and
  `HelloWorld.appendGreeting`, with `main` supplying
  `Clock.systemDefaultZone()`, and verify with
  `src/test/groovy/com/example/HelloWorldSpec.groovy` that saving a
  greeting with a fixed `Clock` writes a line prefixed
  `yyyy-MM-dd HH:mm ` matching that clock's exact time.

## 2. Read and display timestamped and legacy history lines

- [ ] 2.1 Update `HelloWorld.readHistory`/`HelloWorld.run` so a stored line
  is displayed unchanged whether or not it has a `yyyy-MM-dd HH:mm ` prefix,
  and verify with `HelloWorldSpec` that a line with a timestamp prints with
  it and a line without one prints without it.
- [ ] 2.2 Verify with `HelloWorldSpec` that a history file mixing
  timestamp-less and timestamped lines displays each line exactly as
  stored, in the existing reverse-chronological, 5-line-limited order.

## 3. Regression check

- [ ] 3.1 Run `./gradlew test` and confirm all `HelloWorldSpec` scenarios
  pass, including the pre-existing greeting-count and history-limit
  scenarios unaffected by this change.
