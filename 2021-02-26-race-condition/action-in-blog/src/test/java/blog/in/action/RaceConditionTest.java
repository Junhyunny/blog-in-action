package blog.in.action;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

class SharedResource {
    int state;

    void increase() {
        this.state++;
    }

    void decrease() {
        this.state--;
    }

    void initialize() {
        this.state = 0;
    }

    boolean isCorrupted() {
        return this.state != 0;
    }
}

public class RaceConditionTest {

    @Test
    void race_condition() {
        int corruptedStateCount = 0;
        SharedResource sharedResource = new SharedResource();
        for (int index = 0; index < 100000; index++) {
            sharedResource.initialize();
            CompletableFuture<Void> thread1 = CompletableFuture.runAsync(() -> {
                for (int subIndex = 0; subIndex < 100; subIndex++) {
                    sharedResource.increase();
                }
            });
            CompletableFuture<Void> thread2 = CompletableFuture.runAsync(() -> {
                for (int subIndex = 0; subIndex < 100; subIndex++) {
                    sharedResource.decrease();
                }
            });
            thread1.join();
            thread2.join();
            if (sharedResource.isCorrupted()) {
                corruptedStateCount++;
            }
        }
        System.out.println(String.format("Corrupted shared resource count - %s", corruptedStateCount));
    }

    @Test
    void race_condition_with_synchronized() {
        int corruptedStateCount = 0;
        SharedResource sharedResource = new SharedResource();
        for (int index = 0; index < 100000; index++) {
            sharedResource.initialize();
            CompletableFuture<Void> thread1 = CompletableFuture.runAsync(() -> {
                for (int subIndex = 0; subIndex < 100; subIndex++) {
                    synchronized (sharedResource) {
                        sharedResource.increase();
                    }
                }
            });
            CompletableFuture<Void> thread2 = CompletableFuture.runAsync(() -> {
                for (int subIndex = 0; subIndex < 100; subIndex++) {
                    synchronized (sharedResource) {
                        sharedResource.decrease();
                    }
                }
            });
            thread1.join();
            thread2.join();
            if (sharedResource.isCorrupted()) {
                corruptedStateCount++;
            }
        }
        System.out.println(String.format("Corrupted shared resource count - %s", corruptedStateCount));
    }
}
