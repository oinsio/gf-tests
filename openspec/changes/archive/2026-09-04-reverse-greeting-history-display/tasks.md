## 1. Implementation

- [x] 1.1 In `HelloWorld.run`, reverse the `displayedHistory` slice before
      printing so the most recent entry prints first, and move the
      `и ещё N ранее.` print statement to after the displayed lines. Verify
      with `HelloWorldSpec`: "should print history newest first when history
      exceeds the display limit".
- [x] 1.2 Verify the greeting-number output and history selection (which
      entries are shown, still 5 max) are unaffected by the reorder. Verify
      with `HelloWorldSpec`: "should still report the total greeting count
      after reversing the display order".

## 2. Test updates

- [x] 2.1 Update existing `HelloWorldSpec` cases that assert oldest-first
      order or a summary line before the entries to expect newest-first
      order and the summary line after the entries, covering: more than 5
      entries, exactly 5 entries, fewer than 5 entries, and empty history.
      Verify by running `./gradlew test` and confirming `HelloWorldSpec`
      passes.
