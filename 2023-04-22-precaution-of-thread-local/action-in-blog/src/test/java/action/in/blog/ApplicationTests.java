
package action.in.blog;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationTests {

    void submitTasks(int taskCount, ExecutorService executorService, Runnable runnable) {
        for (int index = 0; index < taskCount; index++) {
            executorService.submit(runnable);
        }
    }

    @Test
    void dirty_context_problem_when_using_thread_local_with_thread_pool() {

        int taskCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(taskCount);

        submitTasks(taskCount, executorService, () -> {
            String value = ContextHolder.get();
            if (value != null) {
                System.out.printf("Value is not null. Existed value is %s.%n", value);
            } else {
                System.out.println("Value is null. Set current thread name into holder.");
                ContextHolder.set(Thread.currentThread().getName());
            }
            latch.countDown();
        });
    }

    @Test
    void solving_the_problem_when_using_thread_local_with_thread_pool() {

        int taskCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(taskCount);

        submitTasks(taskCount, executorService, () -> {
            String value = ContextHolder.get();
            if (value != null) {
                System.out.printf("Value is not null. Existed value is %s.%n", value);
            } else {
                System.out.println("Value is null. Set current thread name into holder.");
                ContextHolder.set(Thread.currentThread().getName());
            }
            ContextHolder.remove();
            latch.countDown();
        });
    }
}

class ContextHolder {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String get() {
        return threadLocal.get();
    }

    public static void set(String value) {
        threadLocal.set(value);
    }

    public static void remove() {
        threadLocal.remove();
    }
}