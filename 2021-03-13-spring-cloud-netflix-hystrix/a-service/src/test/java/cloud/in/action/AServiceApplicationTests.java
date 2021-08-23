package cloud.in.action;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "a-service")
interface ASerivceClient {

    @GetMapping(path = "/timeout")
    String requestWithTimeout();

    @GetMapping(path = "/exception")
    String requestWithException();
}

@Log4j2
@SpringBootTest(value = {"spring.application.name=a-service-test",
    "feign.circuitbreaker.enabled=false",
    "feign.client.config.default.connectTimeout=100000",
    "feign.client.config.default.readTimeout=100000"})
class AServiceApplicationTests {

    @Autowired
    private ASerivceClient client;

    @Test
    void test() {
        log.info("requestWithTimeout response: " + client.requestWithTimeout());
        log.info("requestWithException response: " + client.requestWithException());
    }
}
