package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/main")
    public String index() {
        return "resource for authenticated users";
    }
}
