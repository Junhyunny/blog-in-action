package action.in.blog;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MonitorTests {

    @Test
    void test() {

        Monitor monitor = new Monitor(10000);

        List<CompletableFuture> futures = new ArrayList<>();
        futures.add(CompletableFuture.runAsync(monitor::increase));
        futures.add(CompletableFuture.runAsync(monitor::decrease));
        futures.forEach(future -> future.join());

        assertThat(monitor.value, equalTo(0));
    }
}

class Monitor {

    Object lock = new Object();

    int value = 0;
    int loopCount = 0;

    public Monitor(int loopCount) {
        this.loopCount = loopCount;
    }

    void increase() {
        for (int index = 0; index < loopCount; index++) {
            synchronized (lock) {
                lock = new Object();
                value++;
            }
        }
    }

    void decrease() {
        for (int index = 0; index < loopCount; index++) {
            synchronized (lock) {
                lock = new Object();
                value--;
            }
        }
    }
}