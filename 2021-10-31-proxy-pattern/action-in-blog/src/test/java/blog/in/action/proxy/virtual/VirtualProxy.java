package blog.in.action.proxy.virtual;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class VirtualProxy implements VirtualSubject {

    private VirtualRealSubject realSubject;

    public VirtualProxy() {
        realSubject = new VirtualRealSubject();
    }

    @Override
    public void print() throws InterruptedException {
        while (!realSubject.isReady()) {
            Thread.sleep(500);
            log.info("waiting");
        }
        realSubject.print();
    }
}
