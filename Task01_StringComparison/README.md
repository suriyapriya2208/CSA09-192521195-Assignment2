# Q1 – Building Text Under Load

Compile/run: `javac StringComparison.java` then `java StringComparison`.

Three workloads are defined in `workloads`: 10×6, 10,000×50, and 50,000×20. Each implementation is warmed up and measured five times. Copy the CSV output into `results.csv` and discuss noise/GC/JIT effects. The estimated number of intermediate concatenation results for repeated `+` is workload-dependent; explain that each reassignment in the loop creates a new String result, while StringBuilder/StringBuffer append to a mutable buffer.

Complexity: for a ticket with n fields and total output length L, repeated immutable concatenation can copy growing prefixes and cost O(L·n) in a simplified equal-sized-field model (often described as quadratic in n); StringBuilder/StringBuffer append are amortized O(L). StringJoiner can also build a delimited result in O(L). Per-ticket text: StringBuilder. Shared concurrent log: StringBuffer protects individual methods, but use an explicit synchronized block/lock for multi-call logical records, or a thread-safe logging facility.
