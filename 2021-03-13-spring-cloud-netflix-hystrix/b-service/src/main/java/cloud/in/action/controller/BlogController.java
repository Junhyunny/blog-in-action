package cloud.in.action.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class BlogController {

    @GetMapping(value = "/timeout")
    public String requestWithTimeoutException() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return "no time out occur";
    }

    private boolean implementationException() {
        return true;
    }

    @GetMapping(value = "/exception")
    public String requestWithIntentionalException() {
        if (implementationException()) {
            throw new RuntimeException("exception occur");
        }
        return "no exception occur";
    }
}
