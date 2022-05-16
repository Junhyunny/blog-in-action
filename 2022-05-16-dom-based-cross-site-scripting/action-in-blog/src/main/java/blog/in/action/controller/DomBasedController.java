package blog.in.action.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DomBasedController {

    @GetMapping(path = {"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("first")
    public String first() {
        return "First";
    }

    @GetMapping("second")
    public String second() {
        return "Second";
    }
}
