package blog.in.action.raceccondition;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class NotSynchronizedTest {

    class Resource {

        int data = 0;

        public void add(int value) {
            data += value;
        }

        public void substract(int value) {
            data -= value;
        }
    }

    class T1 extends Thread {

        int time;
        Resource resource;

        public T1(int time, Resource resource) {
            this.time = time;
            this.resource = resource;
        }

        @Override
        public void run() {
            // 1번 스레드의 임계 영역
            for (int index = 0; index < time; index++) {
                resource.add(1);
            }
        }
    }

    class T2 extends Thread {

        int time;
        Resource resource;

        public T2(int time, Resource resource) {
            this.time = time;
            this.resource = resource;
        }

        @Override
        public void run() {
            // 2번 스레드의 임계 영역
            for (int index = 0; index < time; index++) {
                resource.substract(1);
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        int result = 0;
        int times = 1000;
        for (int index = 0; index < times; index++) {
            Resource sharedResource = new Resource();
            T1 t1 = new T1(100, sharedResource);
            T2 t2 = new T2(100, sharedResource);
            t1.start();
            t2.start();
            t1.join();
            t2.join();
            if (sharedResource.data == 0) {
                result++;
            }
        }
        log.info("정상적인 결과 / 총 테스트 시도 = " + result + " / " + times);
    }
}
