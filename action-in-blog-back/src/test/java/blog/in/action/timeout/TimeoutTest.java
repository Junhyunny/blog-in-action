package blog.in.action.timeout;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(value = {"server.port=8080"})
public class TimeoutTest {

    private RestTemplate template = new RestTemplateBuilder()
        // .setReadTimeout(Duration.ofMillis(1))
        .setConnectTimeout(Duration.ofNanos(1))
        .build();

    @Test
    public void test() {
        List<CompletableFuture<Void>> threads = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            threads.add(CompletableFuture.runAsync(() -> {
                for (int subIndex = 0; subIndex < 1; subIndex++) {
                    template.getForObject("http://localhost:8081/api/foo", String.class);
                }
            }));
        }
        for (CompletableFuture future : threads) {
            future.join();
        }
    }
}
