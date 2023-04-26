package action.in.blog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
class ScheduledThreadPoolTests {

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
    void run_scheduled_task() throws InterruptedException {
        int taskCount = 10;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
        executorService.schedule(() -> {
            log.info("Run at last");
        }, 1000, TimeUnit.MILLISECONDS);
        submitTasks(taskCount, executorService, () -> {
            log.info("Hello World");
        });


        int poolSize = ((ThreadPoolExecutor) executorService).getPoolSize();
        assertThat(poolSize, equalTo(5));

        waitFor(5000);

        int poolSizeAfterWait = ((ThreadPoolExecutor) executorService).getPoolSize();
        assertThat(poolSizeAfterWait, equalTo(5));
    }

    @Test
    void remove_thread_when_core_size_zero() throws InterruptedException {
        int taskCount = 10;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
        submitTasks(taskCount, executorService, () -> {
            log.info("Hello World");
        });

        int poolSize = ((ThreadPoolExecutor) executorService).getPoolSize();
        assertThat(poolSize, equalTo(1));

        waitFor(50);

        int poolSizeAfterWait = ((ThreadPoolExecutor) executorService).getPoolSize();
        assertThat(poolSizeAfterWait, equalTo(0));
    }
}
