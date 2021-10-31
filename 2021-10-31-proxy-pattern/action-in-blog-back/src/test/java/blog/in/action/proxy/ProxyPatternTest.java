package blog.in.action.proxy;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Log4j2
public class ProxyPatternTest {

    private Client client;

    @BeforeEach
    public void beforeEach() {
        client = new Client();
    }

    @Test
    public void when_hasAccessibleAuthorization_then_print() {
        client.printNormalThingByProtectionProxy();
    }

    @Test
    public void when_hasNotAccessibleAuthorization_then_occurException() {
        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () -> {
            client.printAdminThingByProtectionProxy();
        });
        log.info(throwable.getMessage());
    }

    @Test
    public void when_printGoogleByRemoteProxy_then_printHTML() {
        client.printGoogleByRemoteProxy();
    }

    @Test
    public void when_printByVirtualProxy_then_printFewSecondsLater() throws InterruptedException {
        client.printByVirtualProxy();
    }

    @Test
    public void when_printByVirtualProxyAfterCountFive_then_printRightAfterCount() throws InterruptedException {
        client.printByVirtualProxyAfterCountFive();
    }
}
