package blog.in.action.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class AtomicIntegerTest {

    class NormalInteger {

        volatile int value;

        NormalInteger(int value) {
            this.value = value;
        }
    }

    class SynchronizedThread extends Thread {

        boolean operation;

        NormalInteger normalInteger;

        public SynchronizedThread(boolean operation, NormalInteger normalInteger) {
            this.operation = operation;
            this.normalInteger = normalInteger;
        }

        void add() {
            synchronized (normalInteger) {
                normalInteger.value++;
            }
        }

        void subtract() {
            synchronized (normalInteger) {
                normalInteger.value--;
            }
        }

        @Override
        public void run() {
            int limit = Integer.MAX_VALUE / 10;
            for (int index = 0; index < limit; index++) {
                if (operation) {
                    add();
                } else {
                    subtract();
                }
            }
        }
    }

    class AtomicThread extends Thread {

        boolean operation;

        AtomicInteger atomicInteger;

        public AtomicThread(boolean operation, AtomicInteger atomicInteger) {
            this.operation = operation;
            this.atomicInteger = atomicInteger;
        }

        void add() {
            atomicInteger.incrementAndGet();
        }

        void subtract() {
            atomicInteger.decrementAndGet();
        }

        @Override
        public void run() {
            int limit = Integer.MAX_VALUE / 10;
            for (int index = 0; index < limit; index++) {
                if (operation) {
                    add();
                } else {
                    subtract();
                }
            }
        }
    }

    @Test
    public void synchronized_test() throws InterruptedException {
        long start = System.currentTimeMillis();
        NormalInteger integer = new NormalInteger(0);
        Thread addTh = new SynchronizedThread(true, integer);
        Thread subTh = new SynchronizedThread(false, integer);
        addTh.start();
        subTh.start();
        addTh.join();
        subTh.join();
        long end = System.currentTimeMillis();
        log.info("operation time: " + (end - start) + ", value: " + integer.value);
    }

    @Test
    public void atomic_test() throws InterruptedException {
        long start = System.currentTimeMillis();
        AtomicInteger integer = new AtomicInteger(0);
        Thread addTh = new AtomicThread(true, integer);
        Thread subTh = new AtomicThread(false, integer);
        addTh.start();
        subTh.start();
        addTh.join();
        subTh.join();
        long end = System.currentTimeMillis();
        log.info("operation time: " + (end - start) + ", value: " + integer.get());
    }
}
