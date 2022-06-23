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
class HystrixTestService {

    @HystrixCommand(fallbackMethod = "fallbackHystrixTest",
        commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "20"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "3000")})
    public String getHystrixTest(int index) {
        return new RestTemplate().getForObject("http://localhost:8000/hystrix-test/" + index, String.class);
    }

    public String fallbackHystrixTest(int index) {
        return "fallback hystrix test";
    }
}

@Log4j2
@EnableCircuitBreaker
@SpringBootTest
public class HystrixTest {

    @Autowired
    private HystrixTestService service;

    @Test
    public void test() {
        for (int index = 0; index < 100; index++) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            long start = System.currentTimeMillis();
            String response = service.getHystrixTest(index);
            long end = System.currentTimeMillis();
            log.info("index: " + index + ", waiting time: " + (end - start) + " ms, response: " + response);
        }
    }
}
