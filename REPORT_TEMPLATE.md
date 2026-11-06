Perfect! I've filled in your report using the actual outputs you shared from all 7 tasks.

I've also added your register number (`192521195`) and submission date. The only details you still need to add are your working GitHub repository URL and any missing evidence.

Copy the complete report below and replace the contents of `REPORT_TEMPLATE.md`.

# Java Programming – Assignment 2 (CO2)

**Student Name:** Suriya Priya K.\
**Register Number:** 192521195\
**Course:** Java Programming\
**Assignment:** Assignment 2 – CO2\
**GitHub Repository:** [Paste your working repository URL]\
**Submission Date:** 06 November 2026

## Introduction

This assignment investigates Java strings, collection framework implementations, equality and hashing, maps, generics, comparators and priority queues. Each task follows Predict, Design, Implement, Measure, Analyse and Evaluate.

## Q1 – Building Text Under Load

**Prediction:** String is immutable, so repeated `+` creates intermediate String results. StringBuilder uses a mutable buffer and is expected to be efficient for local ticket construction. StringBuffer has synchronized methods and may add overhead.

**Workloads:**

- 10 tickets × 6 fields
- 10,000 tickets × 50 fields
- 50,000 tickets × 20 fields

**Actual measured results:**

| Workload  | Method        | Average time (ms) | Memory before (bytes) | Memory after (bytes) |
| --------- | ------------- | ----------------- | --------------------- | -------------------- |
| 10×6      | String        | 0.647             | 680704                | 2064888              |
| 10×6      | StringBuilder | 0.086             | 1085304               | 1169208              |
| 10×6      | StringBuffer  | 0.296             | 1083872               | 1129160              |
| 10,000×50 | String        | 44.146            | 1085448               | 112237368            |
| 10,000×50 | StringBuilder | 18.770            | 1085512               | 59807832             |
| 10,000×50 | StringBuffer  | 64.102            | 1085512               | 38837112             |
| 50,000×20 | String        | 21.850            | 1085512               | 114334104            |
| 50,000×20 | StringBuilder | 16.492            | 1085512               | 3184728              |
| 50,000×20 | StringBuffer  | 101.814           | 1085512               | 40933464             |

**Analysis:** StringBuilder was the fastest method in all three measured workloads. StringBuffer was slower in these tests because of synchronization overhead. The results generally support the prediction.

Memory snapshots are approximate JVM heap observations and are affected by garbage collection. They are not direct allocation counts.

**Decision:** Use StringBuilder for a per-ticket buffer. For a shared log, StringBuffer synchronizes individual operations, but multi-step log records need one synchronized transaction/lock or a thread-safe logging system.

**Alternative:** StringJoiner is useful for delimiter-separated fields. Repeated immutable concatenation can repeatedly copy accumulated prefixes, while builder append is amortized linear in total output length.

## Q2 – List Choice

**Actual measured results:**

| Operation                | Size   | ArrayList | LinkedList |
| ------------------------ | ------ | --------- | ---------- |
| Get middle (avg ns)      | 1,000  | 138.6     | 1821.2     |
| Get middle (avg ns)      | 5,000  | 65.2      | 4327.8     |
| Get middle (avg ns)      | 10,000 | 57.8      | 7930.1     |
| Full iteration (ns)      | 1,000  | 194200    | 1002500    |
| Full iteration (ns)      | 5,000  | 486100    | 402000     |
| Full iteration (ns)      | 10,000 | 2141100   | 2357500    |
| End add batch (ns)       | 1,000  | 82600     | 114800     |
| End add batch (ns)       | 5,000  | 346100    | 334100     |
| End add batch (ns)       | 10,000 | 573800    | 508800     |
| Front insert batch (ns)  | 1,000  | 5088900   | 264200     |
| Front insert batch (ns)  | 5,000  | 1628500   | 480300     |
| Front insert batch (ns)  | 10,000 | 5996000   | 632500     |
| Middle remove batch (ns) | 1,000  | 413000    | 625200     |
| Middle remove batch (ns) | 5,000  | 1152200   | 25791000   |
| Middle remove batch (ns) | 10,000 | 4900900   | 37458900   |

**Queue output:**

- Up Next (front to back): [1, 2, 3]
- Serve next: 1

**Analysis:** ArrayList provides fast indexed access because elements are stored in an array. LinkedList requires traversal for indexed access. LinkedList performed better in the front-insertion measurements, while ArrayList performed better in several indexed-access measurements. Individual timings vary with JVM and machine conditions.

**Complexity comparison:**

| Operation       | ArrayList      | LinkedList |
| --------------- | -------------- | ---------- |
| Indexed access  | O(1)           | O(n)       |
| Add at end      | Amortized O(1) | O(1)       |
| Insert at front | O(n)           | O(1)       |
| Remove at front | O(n)           | O(1)       |
| Search          | O(n)           | O(n)       |
| Iteration       | O(n)           | O(n)       |

ArrayDeque is appropriate for an Up Next queue because adding and removing at both ends is efficient.

## Q3 – HashSet Contract

**Actual output:**

```
Unique students: 2
Designed duplicate test size: 2
Correct contract: duplicate register numbers are retained only once.
```

**Analysis:** Equal objects must return equal hash codes. HashSet uses hashCode() to select a bucket and equals() to check candidates. Changing identity fields after insertion can make lookup or removal unreliable. Therefore, identity fields should be immutable.

HashSet provides average O(1) insertion, search and removal. TreeSet provides O(log n) operations and maintains sorted order.

**Result:** The duplicate student test passed successfully.

## Q4 – Map Comparison

**Actual measured results:**

```
HashMap,size=1000,lookup100k_ms=15.123,iteration_ms=0.791,firstKeys=M0000064,M0000063,M0000066,M0000065,M0000068,M0000067,M00000
TreeMap,size=1000,lookup100k_ms=21.194,iteration_ms=0.377,firstKeys=M0000000,M0000001,M0000002,M0000003,M0000004,M0000005,M00000
LinkedHashMap,size=1000,lookup100k_ms=9.983,iteration_ms=0.739,firstKeys=M0000000,M0000001,M0000002,M0000003,M0000004,M0000005,M00000
HashMap,size=10000,lookup100k_ms=47.644,iteration_ms=9.122,firstKeys=M0005319,M0005318,M0006649,M0005313,M0006644,M0007976,M00053
TreeMap,size=10000,lookup100k_ms=22.357,iteration_ms=2.090,firstKeys=M0000000,M0000001,M0000002,M0000003,M0000004,M0000005,M00000
LinkedHashMap,size=10000,lookup100k_ms=15.491,iteration_ms=7.927,firstKeys=M0000000,M0000001,M0000002,M0000003,M0000004,M0000005,M00000
HashMap,size=50000,lookup100k_ms=11.650,iteration_ms=13.351,firstKeys=M0032936,M0032937,M0032938,M0032939,M0007976,M0007977,M00079
TreeMap,size=50000,lookup100k_ms=95.253,iteration_ms=10.242,firstKeys=M0000000,M0000001,M0000002,M0000003,M0000004,M0000005,M00000
LinkedHashMap,size=50000,lookup100k_ms=15.596,iteration_ms=5.604,firstKeys=M0000000,M0000001,M0000002,M0000003,M0000004,M0000005,M00000
HashMap: no ordering guarantee; TreeMap: key-sorted; LinkedHashMap: insertion order.
```

**Analysis:**

- HashMap provides average O(1) lookup but does not guarantee order.
- TreeMap maintains sorted keys and provides O(log n) operations.
- LinkedHashMap maintains insertion order and provides average O(1) lookup.

**Decision:** Use HashMap when ordering is unnecessary, TreeMap when sorted keys are required, and LinkedHashMap when insertion order must be preserved. Hashtable is generally considered a legacy collection; modern alternatives are usually preferred.

The measurements are machine- and JVM-dependent, so they should not be treated as fixed performance guarantees.

## Q5 – Generic Leaderboard

**Actual output:**

```
[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]
```

**Analysis:** The leaderboard uses the following ranking rules:

1. Higher points first.
2. Higher goal difference first.
3. Team name in alphabetical order.

The generic class uses Comparator\<? super T> and sorts a copy of the collection. Sorting takes O(n log n) time.

**Result:** The leaderboard displayed the teams in the expected order, including the tie-break between Bisons and Panthers.

## Q6 – Hospital Triage

**Actual output:**

```
Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)
Expected order: Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)
```

**Analysis:** Patients are served according to severity priority. An arrival counter is used to maintain arrival order when patients have the same severity.

PriorityQueue insertion and removal take O(log n) time, while peek takes O(1) time.

Directly printing a PriorityQueue does not guarantee sorted order. Repeatedly removing its head produces the priority order.

**Fairness:** A continuous arrival of high-priority patients may cause starvation for lower-priority patients. An aging mechanism or waiting-time rule can help improve fairness.

**Result:** Actual patient order matched the expected order.

## Q7 – Diagnose, Redesign and Validate

**Bottleneck:** Repeated String concatenation rebuilds immutable strings and copies previous content.

**Objective:** Reduce intermediate string creation and construction time while preserving exact ticket output.

**Redesign:** Use one StringBuilder per ticket and append each field.

**Prediction:** Lower construction time and fewer intermediate String objects for larger tickets.

**Actual measured results:**

| Method                     | Time       |
| -------------------------- | ---------- |
| Original repeated String + | 167.713 ms |
| Redesigned StringBuilder   | 14.482 ms  |

**Output validation:**

```
Same output: true
```

**Analysis:** StringBuilder completed the measured task faster than repeated String concatenation. The output equality check confirms that both implementations produced the same result. Timing can vary due to the machine, JVM and garbage collection.

**Result:** The redesign improved the measured construction time while preserving the output.

## Conclusion

This assignment demonstrated how different Java classes are suitable for different requirements. StringBuilder is efficient for repeated text construction. ArrayList is suitable for indexed access, while LinkedList is useful for certain insertion and removal patterns. HashSet avoids duplicate elements when equals() and hashCode() are correctly implemented. HashMap, TreeMap and LinkedHashMap provide different ordering behaviours. Comparator supports flexible sorting, and PriorityQueue supports priority-based processing.

The practical results helped compare theoretical time complexities with actual execution measurements.

## References

1. Oracle Java Documentation – String, StringBuilder and StringBuffer.
2. Oracle Java Documentation – Collections Framework, List, Set, Map, Queue and Deque.
3. Oracle Java Documentation – Comparator, Comparable and PriorityQueue.

