public class RedesignComparison {
    static volatile String sink;
    static String original(int fields) {
        String s="";
        for(int i=0;i<fields;i++) s += "FIELD"+i+"=VALUE"+i+";";
        return s;
    }
    static String redesigned(int fields) {
        StringBuilder b=new StringBuilder();
        for(int i=0;i<fields;i++) b.append("FIELD").append(i).append("=VALUE").append(i).append(';');
        return b.toString();
    }
    static long measure(boolean builder,int fields,int repetitions) {
        long start=System.nanoTime();
        for(int r=0;r<repetitions;r++) sink=builder?redesigned(fields):original(fields);
        return System.nanoTime()-start;
    }
    public static void main(String[] args) {
        int fields=200, reps=2000;
        for(int i=0;i<2;i++){measure(false,fields,100);measure(true,fields,100);}
        long oldNs=measure(false,fields,reps);
        long newNs=measure(true,fields,reps);
        System.out.printf("Original repeated String + : %.3f ms%n",oldNs/1e6);
        System.out.printf("Redesigned StringBuilder   : %.3f ms%n",newNs/1e6);
        System.out.println("Same output: "+original(fields).equals(redesigned(fields)));
        System.out.println("Use repeated runs and your own machine's measurements in the report.");
    }
}