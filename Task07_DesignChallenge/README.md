# Q7 – Diagnose, Redesign and Validate

This design challenge selects Q1's repeated String concatenation as the bottleneck and redesigns ticket construction with StringBuilder.

Compile/run: `javac RedesignComparison.java` then `java RedesignComparison`.

Record original and redesigned timings from the same run conditions. Both methods must produce identical text. Explain: immutable String concatenation repeatedly creates new strings and copies accumulated content; StringBuilder appends to a mutable buffer, reducing repeated copying. Trade-off: StringBuilder uses a mutable backing buffer and should not be shared unsafely between threads; use a separate builder per ticket. The output string is still created once by `toString()`.

Pseudocode:
1. Create StringBuilder.
2. For each ticket field, append label, value and delimiter.
3. Convert builder to String once.
4. Compare output and repeated timing with original implementation.
