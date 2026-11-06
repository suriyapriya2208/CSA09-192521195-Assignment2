# Q4 – Pharmacy Inventory Maps

Compile/run: `javac MapComparison.java` then `java MapComparison`.

Tests 1,000, 10,000 and 50,000 medicine records, performs 100,000 lookups and times key iteration/report generation. TreeMap naturally emits code-sorted keys; LinkedHashMap preserves insertion order; HashMap has no ordering guarantee. To create a sorted report from HashMap, copy keys and sort them (O(n log n)).

Typical costs: HashMap put/get average O(1), iteration O(n); TreeMap put/get O(log n), iteration O(n); LinkedHashMap put/get average O(1), iteration O(n). All store O(n) entries. Hashtable is legacy synchronized API; modern code usually uses HashMap plus explicit synchronization/concurrent collections when needed.

Define an acceptable lookup threshold for your context (for example, under 1 ms per lookup in this local micro-test) and explain that real deployment also depends on hardware, load and service-level requirements.
