package blog.in.action;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

@Log4j2
public class AtomicIntegerTest {

    final int limit = Integer.MAX_VALUE / 10;
    AtomicInteger atomicInteger;

    @Test
    void test() {
        atomicInteger = new AtomicInteger(0);

        long start = System.currentTimeMillis();

        var increaseThread = CompletableFuture.runAsync(() -> {
            for (int index = 0; index < limit; index++) {
                atomicInteger.incrementAndGet();
            }
        });
        var decreaseThread = CompletableFuture.runAsync(() -> {
            for (int index = 0; index < limit; index++) {
                atomicInteger.decrementAndGet();
            }
        });

        increaseThread.join();
        decreaseThread.join();
        long end = System.currentTimeMillis();
        log.info("operation time: {}", (end - start));
        log.info("value: {}", atomicInteger.get());
    }
}
