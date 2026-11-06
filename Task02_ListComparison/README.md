# Q2 – ArrayList, LinkedList and ArrayDeque

Compile/run: `javac ListComparison.java` then `java ListComparison`.

The benchmark uses three list sizes and tests end-add, front insertion, middle removal, indexed access and iteration. Timings vary with machine/JVM; repeat runs and record your actual output.

Complexity summary:
- ArrayList: append amortized O(1), front/middle insert/remove O(n), get(k) O(1), iteration O(n); contiguous reference array.
- LinkedList: add/remove at ends O(1), indexed get(k) O(n), locating a middle position O(n), iteration O(n); each element has node/link overhead.
- ArrayDeque: add/remove at both ends amortized O(1), no indexed access; suitable for Up Next FIFO/deque behavior.
A plain array with manual shifting has O(n) front/middle insert/remove and O(1) indexed access.
