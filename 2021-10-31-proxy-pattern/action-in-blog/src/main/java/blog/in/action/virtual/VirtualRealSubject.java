package blog.in.action.virtual;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VirtualRealSubject implements VirtualSubject {

    public VirtualRealSubject() {
        try {
            log.info("wait for loading...");
            Thread.sleep(1000);
            log.info("finish");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print() {
        log.info("print something");
    }
}
