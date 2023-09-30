package action.in.blog;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InheritableThreadLocalModeTest {

    @Test
    void inheritableThreadLocalMode() throws InterruptedException {

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        var testingToken = new TestingAuthenticationToken("Junhyunny", "12345", "ROLE_USER");
        var securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(testingToken);
        SecurityContextHolder.setContext(securityContext);


        var contextArray = new SecurityContext[2];
        var thread = new Thread(() -> {
            var context = SecurityContextHolder.getContext();
            contextArray[0] = context;
        });
        thread.start();
        thread.join();

        var future = CompletableFuture.runAsync(() -> {
            var context = SecurityContextHolder.getContext();
            contextArray[1] = context;
        });
        future.join();


        var result = SecurityContextHolder.getContext();
        assertEquals(testingToken, result.getAuthentication());
        assertEquals(testingToken, contextArray[0].getAuthentication());
        assertNull(contextArray[1].getAuthentication());
        System.out.println("Fin");
    }

}
