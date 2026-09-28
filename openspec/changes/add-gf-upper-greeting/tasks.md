# Tasks

## 1. Implement GF_UPPER support

- [x] 1.1 Add a package-private `HelloWorld.shouldUpper(String gfUpperValue)` helper that returns true only when the value is exactly `"1"`, and verify with `com.example.HelloWorldSpec` ("GF_UPPER value of 1 is upper-case, other values are not")
- [x] 1.2 In `HelloWorld.run`, upper-case the greeting passed to `out.println` when `shouldUpper(System.getenv("GF_UPPER"))` is true, leaving the `greeting` value used for history counting and `appendGreeting` unchanged, and verify with `com.example.HelloWorldSpec` ("printed greeting is upper-cased when GF_UPPER=1 but the saved greeting is not")

## 2. Verify end-to-end behavior

- [x] 2.1 Add Spock scenarios to `com.example.HelloWorldSpec` covering: GF_UPPER=1 upper-cases the printed greeting, GF_UPPER unset leaves it unchanged, and GF_UPPER=0 leaves it unchanged; run `./gradlew test` and verify all tests pass
