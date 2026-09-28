## 1. Implement GF_UPPER support

- [x] 1.1 In `HelloWorld.main`, read the `GF_UPPER` environment variable and
      upper-case the greeting returned by `greetingFor(args)` before it is
      passed to `run`/`GreetingStats`, only when the value is exactly `1`.
      Verify with `HelloWorldSpec`: "should upper-case the default greeting
      when GF_UPPER is 1".
- [x] 1.2 Verify a personalized greeting is upper-cased end to end (printed,
      counted in history, and saved to `greetings.txt`) when `GF_UPPER=1`.
      Verify with `HelloWorldSpec`: "should upper-case a personalized
      greeting and save it in upper case when GF_UPPER is 1".
- [x] 1.3 Verify the greeting is left unchanged when `GF_UPPER` is unset and
      when it is set to a value other than `1` (e.g. `true`). Verify with
      `HelloWorldSpec`: "should leave the greeting unchanged when GF_UPPER
      is unset or not equal to 1".

## 2. Verify full suite

- [x] 2.1 Run `./gradlew test` and confirm all Spock specs, including the
      new `HelloWorldSpec` scenarios, pass.
