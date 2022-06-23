package blog.in.action.openfeign.dynamic;

import feign.Response;
import java.net.URI;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "dynamic-url-client", url = "any-value")
interface DynamicUrlClient {

    @GetMapping(path = "/")
    Response getMethod(URI uri);
}

@Log4j2
@SpringBootTest
public class DynamicUrlTest {

    @Autowired
    private DynamicUrlClient dynamicUrlClient;

    @Test
    public void test() {
        try {
            Response response = dynamicUrlClient.getMethod(new URI("https://www.naver.com"));
            log.info("response from naver: " + response.body());
            response = dynamicUrlClient.getMethod(new URI("https://www.google.com"));
            log.info("response from google: " + response.body());
        } catch (Exception e) {
            log.error("error while using feignclient", e);
        }
    }
}
