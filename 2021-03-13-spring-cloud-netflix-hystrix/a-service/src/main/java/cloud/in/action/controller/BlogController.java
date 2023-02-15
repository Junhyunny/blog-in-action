package cloud.in.action.controller;

import cloud.in.action.proxy.BlogClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    private final BlogClient blogClient;

    public BlogController(BlogClient blogClient) {
        this.blogClient = blogClient;
    }

    @GetMapping(value = "/timeout")
    public String requestWithTimeoutException() {
        return blogClient.requestWithTimeoutException();
    }

    @GetMapping(value = "/exception")
    public String requestWithIntentionalException() {
        return blogClient.requestWithIntentionalException();
    }
}
