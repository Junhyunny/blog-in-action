package blog.in.action;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class AsyncNonBlockingTest {

    public static void main(String[] args) {
        WorkerA a = new WorkerA();
        WorkerB b = new WorkerB();


        CompletableFuture<Void> joinPoint = b.takeRequest(a.giveRequest()); // 1
        a.doWork(); // 3
        joinPoint.join();
        System.out.println("All workers finish the works.");
    }

    static class WorkerA {

        BiConsumer<String, String> work = (name, message) -> {
            for (int index = 0; index < 5; index++) {
                for (int subIndex = Integer.MIN_VALUE; subIndex < Integer.MAX_VALUE; subIndex++) {
                }
                System.out.printf("%s: doing something for my work.\n", name);
            }
            System.out.printf("%s: %s\n", name, message);
        };

        void doWork() {
            work.accept("A", "I 'm worker A. And I' m done.");
        }

        BiConsumer<String, String> giveRequest() {
            return (name, message) -> {
                for (int index = 0; index < 5; index++) {
                    for (int subIndex = Integer.MIN_VALUE; subIndex < Integer.MAX_VALUE; subIndex++) {
                    }
                    System.out.printf("%s: doing something for request.\n", name);
                }
                System.out.printf("%s: %s\n", name, message);
            };
        }
    }

    static class WorkerB {

        CompletableFuture<Void> takeRequest(BiConsumer<String, String> myWork) {
            return CompletableFuture.runAsync( // 2
                    () -> myWork.accept("B", "I'm worker B. And I'm done.")
            );
        }
    }
}
