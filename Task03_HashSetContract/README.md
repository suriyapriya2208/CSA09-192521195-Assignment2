# Q3 – HashSet and equals/hashCode

Compile/run: `javac Attendance.java` then `java Attendance`.

The supplied complete-code program is implemented with immutable identity field `regNo`. `equals` and `hashCode` both use that field. Expected first line: `Unique students: 2`.

Investigation: when equals is overridden without hashCode, equal objects can be assigned to different hash buckets; HashSet may not call equals across those buckets, so duplicates can remain. Before correction, run a separate version with the hashCode override temporarily removed and record the observed result. Hash codes may collide; equals resolves candidates within a bucket. Never mutate fields used by equals/hashCode while an object is in a hash set.

Alternative: HashMap<String,Student> keyed by regNo offers average O(1) put/get; TreeSet with comparator on regNo offers O(log n). Both use O(n) storage.
