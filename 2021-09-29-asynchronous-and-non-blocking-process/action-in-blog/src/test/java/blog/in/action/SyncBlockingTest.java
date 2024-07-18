package blog.in.action;

import java.util.function.BiConsumer;

public class SyncBlockingTest {

    public static void main(String[] args) {
        WorkerA a = new WorkerA();
        WorkerB b = new WorkerB();


        b.takeRequest(a.giveRequest()); // 1
        a.doWork(); // 2
        System.out.println("All workers finish the works.");
    }

    static class WorkerA {

        BiConsumer<String, String> work = (name, message) -> {
            for (int index = 0; index < 5; index++) {
                for (int subIndex = 0; subIndex < Integer.MAX_VALUE; subIndex++) {
                }
                System.out.printf("%s: doing something for my work.\n", name);
            }
            System.out.printf("%s: %s\n", name, message);
        };

        void doWork() {
            work.accept("A", "I'm worker A. And I'm done.");
        }

        BiConsumer<String, String> giveRequest() {
            return (name, message) -> {
                for (int index = 0; index < 5; index++) {
                    for (int subIndex = 0; subIndex < Integer.MAX_VALUE; subIndex++) {
                    }
                    System.out.printf("%s: doing something for request.\n", name);
                }
                System.out.printf("%s: %s\n", name, message);
            };
        }
    }

    static class WorkerB {

        void takeRequest(BiConsumer<String, String> request) {
            request.accept("B", "I'm worker B. And I'm done.");
        }
    }
}
