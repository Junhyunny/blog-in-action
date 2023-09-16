package blog.in.action.virtual;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class VirtualClientTest {

    @Test
    void printByVirtualProxy() {

        var start = System.currentTimeMillis();
        var sut = new VirtualClient(new VirtualProxy());
        sut.print();
        var end = System.currentTimeMillis();


        log.info("{} milli seconds", end - start);
    }

    @Test
    void printByVirtualRealSubject() {

        var start = System.currentTimeMillis();
        var sut = new VirtualClient(new VirtualRealSubject());
        sut.print();
        var end = System.currentTimeMillis();

        log.info("{} milli seconds", end - start);
    }
}