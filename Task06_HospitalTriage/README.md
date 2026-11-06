# Q6 – Hospital Emergency Triage

Compile/run: `javac Triage.java` then `java Triage`.

Expected treatment order: `Meena(S1) Kumar(S1) Lata(S2) Ravi(S3) John(S3) Asha(S5)`.

PriorityQueue comparator sorts lower severity number first, then lower arrival counter. `offer` and `poll` are O(log n), peek O(1), storage O(n). Printing the queue directly does not guarantee sorted iteration; repeatedly poll a copy to display treatment order.

Starvation: minor cases may wait indefinitely if higher-severity patients continuously arrive. A fair policy can increase effective priority after a waiting-time threshold, while preserving clinical safety. A city hospital's 5,000 daily arrivals is not by itself a large queue for O(log n), but concurrency, peak arrivals, clinical rules, persistence and operational validation matter.

Alternatives: sorted LinkedList insertion O(n), removal next O(1); five FIFO queues allow O(1) admission/removal with severity selection across five queues O(1), storage O(n).
