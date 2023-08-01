package blog.in.action.volatilekeyword;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

@Getter
class NormalInteger {
    private volatile int value;

    public NormalInteger(int value) {
        this.value = value;
    }

    public void increase() {
        this.value++;
    }

    public void decrease() {
        this.value--;
    }
}

@Log4j2
public class VolatileTest {

    int limit = Integer.MAX_VALUE / 10;

    @Test
    public void test() {

        var start = System.currentTimeMillis();
        var normalInteger = new NormalInteger(0);

        var firstThread = CompletableFuture.runAsync(() -> {
            for (int index = 0; index < limit; index++) {
                normalInteger.increase();
            }
        });
        var secondThread = CompletableFuture.runAsync(() -> {
            for (int index = 0; index < limit; index++) {
                normalInteger.decrease();
            }
        });

        firstThread.join();
        secondThread.join();

        var end = System.currentTimeMillis();
        log.info("operation time: {}, value: {}", (end - start), normalInteger.getValue());
    }
}
