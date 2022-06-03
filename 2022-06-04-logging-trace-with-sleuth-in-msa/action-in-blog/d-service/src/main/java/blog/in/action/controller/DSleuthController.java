package blog.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DSleuthController {

    @GetMapping("/d-path")
    public String dPath() {
        return "D-SERVICE";
    }
}
