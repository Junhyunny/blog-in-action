package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    private boolean isIntentionalException() {
        return true;
    }

    @GetMapping("/some-request")
    public String someRequest() {
        if (isIntentionalException()) {
            throw new RuntimeException("This is intentional exception.");
        }
        return "Hello World";
    }
}
