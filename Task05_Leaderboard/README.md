# Q5 – Generic Leaderboard

Compile/run: `javac LeaderboardDemo.java` then `java LeaderboardDemo`.

Expected: `[Eagles(9,6), Bisons(9,4), Panthers(9,4), Cobras(6,1)]`.

The Comparator chain orders points descending, goal difference descending, then name ascending. `Leaderboard<T>` accepts `Comparator<? super T>` and returns a sorted copy, leaving original insertion order unchanged. Bisons precedes Panthers alphabetically because their points and goal difference are equal.

Sorting is O(n log n); copy storage is O(n), with sorting implementation workspace depending on implementation. Comparator is more reusable than Team implementing Comparable when different domains need different ranking rules. A faulty subtraction comparator can overflow; use comparingInt.
