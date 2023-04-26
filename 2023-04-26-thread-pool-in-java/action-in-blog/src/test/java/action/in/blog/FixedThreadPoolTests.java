package action.in.blog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
class FixedThreadPoolTests {

    void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    void submitTasks(int taskCount, ExecutorService executorService, Runnable runnable) {
        for (int index = 0; index < taskCount; index++) {
            executorService.submit(runnable);
        }
    }

    @Test
    void thread_pool_size_is_not_changed() {
        int taskCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        submitTasks(taskCount, executorService, () -> {
            log.info("Hello World");
        });


        int poolSize = ((ThreadPoolExecutor) executorService).getPoolSize();
        assertThat(poolSize, equalTo(5));

        waitFor(5000);

        int poolSizeAfterWait = ((ThreadPoolExecutor) executorService).getPoolSize();
        assertThat(poolSizeAfterWait, equalTo(5));
    }
}