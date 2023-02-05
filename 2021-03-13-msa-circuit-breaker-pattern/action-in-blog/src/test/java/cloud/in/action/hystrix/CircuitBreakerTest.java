package cloud.in.action.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
class CircuitBreakerService {

    private final RestTemplate restTemplate = new RestTemplate();

    private String fallbackPlan(int index) {
        return "fallback plan";
    }

    @HystrixCommand(
            fallbackMethod = "fallbackPlan",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "20"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000")
            }
    )
    public String getPost(int index) {
        String url = String.format("http://localhost:8000/post/%s", index);
        return restTemplate.getForObject(url, String.class);
    }
}

@Log4j2
@EnableCircuitBreaker
@SpringBootTest
public class CircuitBreakerTest {

    @Autowired
    private CircuitBreakerService circuitBreakerService;

    void sleep(int milli) {
        try {
            Thread.sleep(milli);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void circuit_close_open() {
        for (int index = 0; index < 100; index++) {
            sleep(100);
            long start = System.currentTimeMillis();
            String result = circuitBreakerService.getPost(index);
            long end = System.currentTimeMillis();
            log.info(String.format("%s(response time: %s)", result, (end - start)));
        }
    }
}
