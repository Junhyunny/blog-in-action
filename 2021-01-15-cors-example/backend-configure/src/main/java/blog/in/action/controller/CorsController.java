package blog.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {

    @GetMapping("/health")
    public String health() {
        return "It's okay because of global CORS configuration.";
    }
}
