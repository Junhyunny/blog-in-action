package blog.in.action;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.MAX_PRIORITY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class ImmutableTest {

    @Test
    public void change_state_single_thread() {

        final String immutableObject = "Junhyunny";


        assertThat(immutableObject.concat(" Kang"), equalTo("Junhyunny Kang"));
        assertThat(immutableObject, equalTo("Junhyunny"));
    }

    @Test
    public void change_state_with_multi_thread() throws InterruptedException {

        final String immutableObject = "Junhyunny";


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int index = 0; index < 100; index++) {
            final int value = index;
            executorService.submit(() -> {
                immutableObject.concat(" Kang");
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(MAX_PRIORITY, TimeUnit.HOURS);


        assertThat(immutableObject, equalTo("Junhyunny"));
    }
}