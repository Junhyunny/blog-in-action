package blog.in.action.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CSleuthController {

    @GetMapping("/c-path")
    public String cPath() {
        return "C-SERVICE";
    }
}
