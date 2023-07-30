package blog.in.action;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

@Getter
class NormalInteger {
    private volatile int value;

    NormalInteger(int value) {
        this.value = value;
    }

    public synchronized void increase() {
        value++;
    }

    public synchronized void decrease() {
        value--;
    }
}

@Slf4j
public class SynchronizedTest {

    final int limit = Integer.MAX_VALUE / 10;
    NormalInteger normalInteger;

    @Test
    void test() {
        normalInteger = new NormalInteger(0);

        long start = System.currentTimeMillis();

        var increaseThread = CompletableFuture.runAsync(() -> {
            for (int index = 0; index < limit; index++) {
                normalInteger.increase();
            }
        });
        var decreaseThread = CompletableFuture.runAsync(() -> {
            for (int index = 0; index < limit; index++) {
                normalInteger.decrease();
            }
        });

        increaseThread.join();
        decreaseThread.join();
        long end = System.currentTimeMillis();
        log.info("operation time: {}", (end - start));
        log.info("value: {}", normalInteger.getValue());
    }
}
