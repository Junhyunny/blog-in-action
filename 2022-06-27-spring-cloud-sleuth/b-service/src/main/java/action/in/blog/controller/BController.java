package action.in.blog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class BController {

    private final Logger logger = LoggerFactory.getLogger(BController.class);

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private void throwException(String key) {
        if ("deny".equals(key)) {
            throw new RuntimeException("throw error");
        }
    }

    @GetMapping(path = {"", "/"})
    public void index(@RequestHeader Map<String, Object> headers, @RequestParam("key") String key) {
        logger.info("header: " + headers + ", key: " + key);
        for (int index = 0; index < 5; index++) {
            logger.info("processing...");
            sleep();
        }
        throwException(key);
    }
}
