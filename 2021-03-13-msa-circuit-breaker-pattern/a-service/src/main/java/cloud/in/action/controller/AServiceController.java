package cloud.in.action.controller;

import java.util.Random;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AServiceController {

    @GetMapping(value = "/hystrix-test/{index}")
    public String requestHystrixTest(@PathVariable(name = "index") Integer index) {
        if (index < 10) {
            return "success";
        } else if (index >= 10 && index < 40 && new Random().nextBoolean()) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } else if (index >= 40 && index < 70 && new Random().nextBoolean()) {
            throw new RuntimeException("exception occur");
        }
        return "success";
    }
}
