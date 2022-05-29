package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarController {

    @GetMapping(path = "/health")
    public String health() {
        return "health";
    }
}
