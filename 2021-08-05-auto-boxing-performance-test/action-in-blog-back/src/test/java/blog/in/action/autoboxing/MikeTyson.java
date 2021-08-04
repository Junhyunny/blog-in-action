package blog.in.action.autoboxing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class MikeTyson implements Runnable {

    private final Map<Integer, OptimizationSnoopInt> map = new HashMap<>();

    public MikeTyson() {
        for (int i = 0; i < 1_000_000; i++) {
            map.put(Integer.valueOf(i), new OptimizationSnoopInt(Integer.valueOf(i)));
        }
    }

    public void run() {
        long yieldCounter = 0;
        while (true) {
            Collection<OptimizationSnoopInt> copyOfValues = map.values();
            for (OptimizationSnoopInt snoopIntCopy : copyOfValues) {
                if (!map.containsKey(snoopIntCopy.getId())) {
                    System.out.println("Now this is strange!");
                }
                if (++yieldCounter % 1000 == 0) {
                    System.out.println("Boxing and unboxing");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws java.io.IOException {
        ThreadGroup threadGroup = new ThreadGroup("Workers");
        Thread[] threads = new Thread[8];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(threadGroup, new MikeTyson(), "Allocator Thread " + i);
            threads[i].setDaemon(true);
            threads[i].start();
        }
        System.out.print("Press to quit!");
        System.out.flush();
        System.in.read();
    }
}
