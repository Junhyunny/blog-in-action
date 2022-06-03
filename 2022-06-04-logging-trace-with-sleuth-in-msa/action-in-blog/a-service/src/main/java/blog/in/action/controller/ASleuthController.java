package blog.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ASleuthController {

    @GetMapping("/a-path")
    public String aPath() {
        return "A-SERVICE";
    }
}
