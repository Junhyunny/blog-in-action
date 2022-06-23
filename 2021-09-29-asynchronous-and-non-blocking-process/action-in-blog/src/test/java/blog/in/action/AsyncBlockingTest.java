package blog.in.action;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AsyncBlockingTest {

    static class WorkerA {

        boolean isWorkBFinished;

        Consumer<String> ownJob = (message) -> {
            for (int index = 0; index < 5; index++) {
                for (int subIndex = Integer.MIN_VALUE; subIndex < Integer.MAX_VALUE; subIndex++) {
                }
                System.out.println("A: doing something.");
            }
            System.out.println("A: " + message);
        };

        Consumer<Void> callMeLater = (Void) -> {
            isWorkBFinished = true;
            System.out.println("B: Hey, Worker A. I'm done.");
        };

        void waitWorkBFinished() {
            while (!isWorkBFinished) {
                System.out.println("A: Waiting for Worker B.");
                for (int subIndex = 0; subIndex < 1000; subIndex++) {
                }
            }
        }

        void doMyWork() {
            ownJob.accept("I'm worker A. And I'm done my first job.");
            waitWorkBFinished();
            ownJob.accept("I'm worker A. And I'm done my second job.");
        }

        Consumer<Void> getCallMeLater() {
            return callMeLater;
        }
    }

    static class WorkerB {

        Consumer<String> ownJob = (message) -> {
            for (int index = 0; index < 5; index++) {
                for (int subIndex = Integer.MIN_VALUE; subIndex < Integer.MAX_VALUE; subIndex++) {
                }
                System.out.println("B: doing something.");
            }
            System.out.println("B: " + message);
        };

        CompletableFuture<Void> doWorkAndCallToALater(Consumer<Void> callBack) {
            return CompletableFuture.runAsync(() -> {
                ownJob.accept("I'm worker B. And I'm my first job.");
                callBack.accept(null);
                ownJob.accept("I'm worker B. And I'm my second job.");
            });
        }
    }

    public static void main(String[] args) {
        WorkerA a = new WorkerA();
        WorkerB b = new WorkerB();
        CompletableFuture<Void> joinPoint = b.doWorkAndCallToALater(a.getCallMeLater());
        a.doMyWork();
        // WorkerB가 일을 마치지 않았는데 메인(main) 스레드가 종료되는 경우 어플리케이션이 종료되므로 이런 현상을 방지하는 코드 추가
        joinPoint.join();
        System.out.println("All workers done.");
    }
}
