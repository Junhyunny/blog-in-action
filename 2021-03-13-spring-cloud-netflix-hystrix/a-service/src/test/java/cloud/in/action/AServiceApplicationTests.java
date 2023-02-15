package cloud.in.action;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test-client", url = "http://localhost:8080")
interface TestClient {

    @GetMapping(path = "/timeout")
    String requestWithTimeoutException();

    @GetMapping(path = "/exception")
    String requestWithIntentionalException();
}

@Log4j2
@SpringBootTest(value = {
        "feign.hystrix.enabled=false",
        "feign.client.config.default.connect-timeout=100000",
        "feign.client.config.default.read-timeout=100000"
})
class AServiceApplicationTests {

    @Autowired
    private TestClient testClient;

    @Test
    void request_api_expect_without_exception() {
        log.info(testClient.requestWithTimeoutException());
        log.info(testClient.requestWithIntentionalException());
    }
}