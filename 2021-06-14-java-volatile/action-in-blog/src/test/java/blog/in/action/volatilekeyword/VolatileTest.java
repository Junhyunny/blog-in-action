package blog.in.action.volatilekeyword;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

@Log4j2
public class VolatileTest {

    class NormalInteger {

        volatile int value;

        NormalInteger(int value) {
            this.value = value;
        }
    }

    class SynchronizedThread extends Thread {

        boolean operation;

        VolatileTest.NormalInteger normalInteger;

        public SynchronizedThread(boolean operation, VolatileTest.NormalInteger normalInteger) {
            this.operation = operation;
            this.normalInteger = normalInteger;
        }

        void add() {
            normalInteger.value++;
        }

        void subtract() {
            normalInteger.value--;
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
        VolatileTest.NormalInteger integer = new VolatileTest.NormalInteger(0);
        Thread addTh = new VolatileTest.SynchronizedThread(true, integer);
        Thread subTh = new VolatileTest.SynchronizedThread(false, integer);
        addTh.start();
        subTh.start();
        addTh.join();
        subTh.join();
        long end = System.currentTimeMillis();
        log.info("operation time: " + (end - start) + ", value: " + integer.value);
    }
}
