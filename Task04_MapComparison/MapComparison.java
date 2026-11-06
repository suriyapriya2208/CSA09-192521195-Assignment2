import java.util.*;

public class MapComparison {
    static volatile int sink;
    static void test(String name, Map<String,Integer> map, int n) {
        for(int i=0;i<n;i++) map.put(String.format(Locale.ROOT,"M%07d",i), i);
        List<String> keys = new ArrayList<>(map.keySet());
        Random random = new Random(42);
        int lookups = 100_000;
        long start = System.nanoTime();
        int sum=0;
        for(int i=0;i<lookups;i++) sum += map.get(keys.get(random.nextInt(keys.size())));
        long lookupNs = System.nanoTime()-start; sink=sum;
        start=System.nanoTime();
        String firstReport = String.join(",", map.keySet());
        long reportNs=System.nanoTime()-start;
        System.out.printf("%s,size=%d,lookup100k_ms=%.3f,iteration_ms=%.3f,firstKeys=%s%n",
                name,n,lookupNs/1e6,reportNs/1e6,firstReport.substring(0,Math.min(60,firstReport.length())));
    }
    public static void main(String[] args) {
        for(int n:new int[]{1000,10000,50000}) {
            test("HashMap",new HashMap<>(),n);
            test("TreeMap",new TreeMap<>(),n);
            test("LinkedHashMap",new LinkedHashMap<>(),n);
        }
        System.out.println("HashMap: no ordering guarantee; TreeMap: key-sorted; LinkedHashMap: insertion order.");
    }
}