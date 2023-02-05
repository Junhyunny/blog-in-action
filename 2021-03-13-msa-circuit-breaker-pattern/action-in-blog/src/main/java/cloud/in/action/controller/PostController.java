package cloud.in.action.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Log4j2
@RestController
public class PostController {

    private void sleep(int milli) {
        try {
            Thread.sleep(milli);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping(value = "/post/{index}")
    public String getPost(@PathVariable(name = "index") Integer index) {
        boolean execution = new Random().nextBoolean();
        String result = String.format("POST(id: %s)", index);
        if (25 <= index && index < 50 && execution) {
            sleep(1000);
        } else if (50 <= index && index < 75 && execution) {
            throw new RuntimeException("occur intentional exception");
        }
        return result;
    }
}
