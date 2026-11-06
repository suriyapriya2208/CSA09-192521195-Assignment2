import java.util.Locale;

public class StringComparison {
    static final int WARMUPS = 2, RUNS = 5;

    static String buildWithString(int tickets, int fields) {
        String last = "";
        for (int t = 0; t < tickets; t++) {
            String ticket = "";
            for (int f = 0; f < fields; f++) {
                ticket = ticket + "FIELD" + f + "=VALUE" + f + ";";
            }
            last = ticket;
        }
        return last;
    }

    static String buildWithBuilder(int tickets, int fields) {
        String last = "";
        for (int t = 0; t < tickets; t++) {
            StringBuilder ticket = new StringBuilder();
            for (int f = 0; f < fields; f++) {
                ticket.append("FIELD").append(f).append("=VALUE").append(f).append(';');
            }
            last = ticket.toString();
        }
        return last;
    }

    static String buildWithBuffer(int tickets, int fields) {
        String last = "";
        for (int t = 0; t < tickets; t++) {
            StringBuffer ticket = new StringBuffer();
            for (int f = 0; f < fields; f++) {
                ticket.append("FIELD").append(f).append("=VALUE").append(f).append(';');
            }
            last = ticket.toString();
        }
        return last;
    }

    static void measure(String label, int tickets, int fields, java.util.function.Supplier<String> work) {
        for (int i = 0; i < WARMUPS; i++) work.get();
        long total = 0;
        String result = "";
        Runtime rt = Runtime.getRuntime();
        System.gc();
        long before = rt.totalMemory() - rt.freeMemory();
        for (int i = 0; i < RUNS; i++) {
            long start = System.nanoTime();
            result = work.get();
            total += System.nanoTime() - start;
        }
        long after = rt.totalMemory() - rt.freeMemory();
        System.out.printf(Locale.US, "%s,%d,%d,%.3f,%d,%d%n",
                label, tickets, fields, total / (double) RUNS / 1_000_000.0,
                before, after);
        if (result.isEmpty()) throw new AssertionError("Unexpected empty result");
    }

    public static void main(String[] args) {
        int[][] workloads = {{10, 6}, {10000, 50}, {50000, 20}};
        System.out.println("method,tickets,fields,average_ms,used_memory_before_bytes,used_memory_after_bytes");
        for (int[] w : workloads) {
            final int tickets = w[0], fields = w[1];
            measure("String", tickets, fields, () -> buildWithString(tickets, fields));
            measure("StringBuilder", tickets, fields, () -> buildWithBuilder(tickets, fields));
            measure("StringBuffer", tickets, fields, () -> buildWithBuffer(tickets, fields));
        }
        System.out.println("\nSample ticket:");
        System.out.println(buildWithBuilder(1, 6));
        System.out.println("Note: memory snapshots are approximate JVM heap observations, not allocation counts.");
    }
}