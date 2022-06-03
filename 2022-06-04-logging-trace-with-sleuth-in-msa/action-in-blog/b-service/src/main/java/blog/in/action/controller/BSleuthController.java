package blog.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BSleuthController {

    @GetMapping("/b-path")
    public String bPath() {
        return "B-SERVICE";
    }
}
