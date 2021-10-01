package blog.in.action;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AsyncNonBlockingTest {

    static class WorkerA {

        Consumer<String> workForA = (message) -> {
            for (int index = 0; index < 5; index++) {
                for (int subIndex = Integer.MIN_VALUE; subIndex < Integer.MAX_VALUE; subIndex++) {
                }
                System.out.println("A: doing something.");
            }
            System.out.println("A: " + message);
        };

        Consumer<String> workForB = (message) -> {
            for (int index = 0; index < 5; index++) {
                for (int subIndex = Integer.MIN_VALUE; subIndex < Integer.MAX_VALUE; subIndex++) {
                }
                System.out.println("B: doing something.");
            }
            System.out.println("B: " + message);
        };

        void doMyWork() {
            workForA.accept("I'm worker A. And I'm done.");
        }

        Consumer<String> getWorkForB() {
            return workForB;
        }
    }

    static class WorkerB {

        CompletableFuture<Void> takeMyWorkAndDoMyWork(Consumer<String> myWork) {
            return CompletableFuture.runAsync(() -> myWork.accept("I'm worker B. And I'm done."));
        }
    }

    public static void main(String[] args) {
        WorkerA a = new WorkerA();
        WorkerB b = new WorkerB();
        CompletableFuture<Void> joinPoint = b.takeMyWorkAndDoMyWork(a.getWorkForB());
        a.doMyWork();
        // WorkerB가 일을 마치지 않았는데 메인(main) 스레드가 종료되는 경우 어플리케이션이 종료되므로 이런 현상을 방지하는 코드 추가
        joinPoint.join();
        System.out.println("All workers done.");
    }
}
