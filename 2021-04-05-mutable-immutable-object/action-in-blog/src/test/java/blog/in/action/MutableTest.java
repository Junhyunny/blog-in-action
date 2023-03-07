package blog.in.action;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.MAX_PRIORITY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class MutableObject {

    private int value;

    public MutableObject(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

@Slf4j
public class MutableTest {

    @Test
    void change_state_single_thread() {

        final MutableObject mutableObject = new MutableObject(0);


        mutableObject.setValue(1);


        assertThat(mutableObject.getValue(), equalTo(1));
    }

    @Test
    void change_state_with_multi_thread() throws InterruptedException {

        final MutableObject mutableObject = new MutableObject(0);


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int index = 0; index < 100; index++) {
            final int value = index;
            executorService.submit(() -> {
                mutableObject.setValue(value);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(MAX_PRIORITY, TimeUnit.HOURS);


        log.info("result - {}", mutableObject.getValue());
    }
}
