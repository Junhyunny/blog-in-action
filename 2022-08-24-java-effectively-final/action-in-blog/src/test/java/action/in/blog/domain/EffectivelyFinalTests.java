package action.in.blog.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class EffectivelyFinalTests {

    int instanceVariable = 0;
    static int staticVariable = 0;

    @Test
    void compiled_when_use_instance_and_static_variable_in_lambda() {
        this.instanceVariable = 10;
        EffectivelyFinalTests.staticVariable = 10;
        Runnable runnable = () -> {
            this.instanceVariable++;
            EffectivelyFinalTests.staticVariable++;
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Test
    void compile_error_when_change_local_variable_in_stream_foreach() {

        int localVariable = 0;

        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.stream().forEach(number -> {
            // localVariable += number;
        });
    }

    @Test
    void compiled_when_read_only_in_stream_foreach() {

        int localVariable = 0;

        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.stream().forEach(number -> {
            System.out.println(localVariable + number);
        });
    }

    @Test
    void compile_error_in_inner_class() {

        int localVariable = 0;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // localVariable++;
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Test
    void parallel_task_with_lambda() {

        int localVariable = 0;

        Runnable runnable = () -> System.out.println(localVariable);

        // Thread 클래스 사용
        Thread thread = new Thread(runnable);
        thread.start();

        // CompletableFuture 클래스 사용
        CompletableFuture.runAsync(runnable);

        // parallel stream 사용
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        numbers.parallelStream().forEach((number) -> System.out.println(localVariable + number));
    }

    Supplier<Integer> getMultiplier(int localVariable) {

        Supplier<Integer> result = () -> localVariable * 2;

        if(new Random().nextBoolean()) {
            // localVariable = 1000;
        }

        return  result;
    }

    @Test
    void localVariableMultiThreading() {
        boolean run = true;
        CompletableFuture.runAsync(() -> {
            while (run) {
                // do operation
            }
        });
        // run = false;
    }
}
