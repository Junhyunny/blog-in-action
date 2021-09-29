package blog.in.action;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AsyncNonBlockingTest {

    static class WorkerA {

        Consumer<String> workForA = (message) -> {
            for (int index = 0; index < 3; index++) {
                for (int subIndex = 0; subIndex < 100000000; subIndex++) {
                }
                System.out.println("A doing something.");
            }
            System.out.println(message);
        };


        Consumer<String> workForB = (message) -> {
            for (int index = 0; index < 3; index++) {
                for (int subIndex = 0; subIndex < 100000000; subIndex++) {
                }
                System.out.println("B doing something.");
            }
            System.out.println(message);
        };

        void doMyWork() {
            workForA.accept("I'm worker A. And I'm done.");
        }

        Consumer<String> getWorkForB() {
            return workForB;
        }
    }

    static class WorkerB {

        Consumer<String> myWork;

        void takeMyWork(Consumer<String> myWork) {
            this.myWork = myWork;
        }

        void doMyWork() {
            CompletableFuture.runAsync(() -> myWork.accept("I'm worker B. And I'm done."));
        }
    }

    public static void main(String[] args) {
        WorkerA a = new WorkerA();
        WorkerB b = new WorkerB();
        b.takeMyWork(a.getWorkForB());
        b.doMyWork();
        a.doMyWork();
    }
}
