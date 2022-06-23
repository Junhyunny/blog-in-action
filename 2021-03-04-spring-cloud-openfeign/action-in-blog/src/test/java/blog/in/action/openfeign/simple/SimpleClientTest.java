package blog.in.action.openfeign.simple;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "simple-client", url = "http://localhost:8080")
interface SimpleClient {

    @GetMapping(path = "/health")
    String health();
}

@Log4j2
@SpringBootTest
public class SimpleClientTest {

    @Autowired
    private SimpleClient simpleClient;

    @Test
    public void test() {
        try {
            String response = simpleClient.health();
            log.info("response from simpleClient: " + response);
        } catch (Exception e) {
            log.error("error while using feignclient", e);
        }
    }
}