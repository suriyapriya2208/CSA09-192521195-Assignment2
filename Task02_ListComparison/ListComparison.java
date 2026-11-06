import java.util.*;
import java.util.function.Supplier;

public class ListComparison {
    static volatile long sink;
    static long time(Runnable r) {
        long s = System.nanoTime(); r.run(); return System.nanoTime() - s;
    }
    static void runSize(int n) {
        List<Integer> a = new ArrayList<>(), l = new LinkedList<>();
        for (int i=0;i<n;i++){ a.add(i); l.add(i); }
        int reps = Math.max(1000, 1000000 / n);
        System.out.printf("\nSIZE=%d%n", n);
        System.out.printf("ArrayList get-middle avg ns: %.1f%n",
                time(() -> { long x=0; for(int i=0;i<reps;i++) x += a.get(n/2); sink=x; })/(double)reps);
        System.out.printf("LinkedList get-middle avg ns: %.1f%n",
                time(() -> { long x=0; for(int i=0;i<reps;i++) x += l.get(n/2); sink=x; })/(double)reps);
        System.out.printf("ArrayList full iteration ns: %d%n",
                time(() -> { long x=0; for(int v:a) x+=v; sink=x; }));
        System.out.printf("LinkedList full iteration ns: %d%n",
                time(() -> { long x=0; for(int v:l) x+=v; sink=x; }));
        System.out.printf("ArrayList end-add batch ns: %d%n",
                time(() -> { List<Integer> x=new ArrayList<>(); for(int i=0;i<n;i++) x.add(i); }));
        System.out.printf("LinkedList end-add batch ns: %d%n",
                time(() -> { List<Integer> x=new LinkedList<>(); for(int i=0;i<n;i++) x.add(i); }));
        System.out.printf("ArrayList front insert batch ns: %d%n",
                time(() -> { List<Integer> x=new ArrayList<>(); for(int i=0;i<n;i++) x.add(0,i); }));
        System.out.printf("LinkedList front insert batch ns: %d%n",
                time(() -> { List<Integer> x=new LinkedList<>(); for(int i=0;i<n;i++) x.add(0,i); }));
        System.out.printf("ArrayList middle remove batch ns: %d%n",
                time(() -> { List<Integer> x=new ArrayList<>(a); while(!x.isEmpty()) x.remove(x.size()/2); }));
        System.out.printf("LinkedList middle remove batch ns: %d%n",
                time(() -> { List<Integer> x=new LinkedList<>(l); while(!x.isEmpty()) x.remove(x.size()/2); }));
    }
    public static void main(String[] args) {
        for (int n : new int[]{1000, 5000, 10000}) runSize(n);
        Deque<Integer> upNext = new ArrayDeque<>();
        upNext.addFirst(2); upNext.addLast(3); upNext.addFirst(1);
        System.out.println("\nUp Next (front to back): " + upNext);
        System.out.println("Serve next: " + upNext.removeFirst());
    }
}