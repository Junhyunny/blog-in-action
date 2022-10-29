package action.in.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class AController {

    private final Logger logger = LoggerFactory.getLogger(AController.class);

    private final RestTemplate restTemplate;

    public AController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    @GetMapping(path = {"", "/"})
    public void index(@RequestHeader Map<String, Object> headers, @RequestParam("key") String key) {
        logger.info("header: " + headers + ", key: " + key);
        for (int index = 0; index < 5; index++) {
            logger.info("processing...");
            sleep();
        }
        restTemplate.getForObject("http://b-service:8081?key=" + key, String.class);
    }
}

@Configuration
class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}