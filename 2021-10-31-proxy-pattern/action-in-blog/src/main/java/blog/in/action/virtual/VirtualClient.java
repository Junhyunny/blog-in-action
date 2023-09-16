package blog.in.action.virtual;

import lombok.extern.log4j.Log4j2;

import java.util.concurrent.ThreadLocalRandom;

@Log4j2
public class VirtualClient {

    private final VirtualSubject virtualSubject;

    public VirtualClient(VirtualSubject virtualSubject) {
        this.virtualSubject = virtualSubject;
    }

    public void print() {
        log.info("business logic inside");
        var result = ThreadLocalRandom.current().nextBoolean();
        if (result) {
            virtualSubject.print();
        }
    }
}
