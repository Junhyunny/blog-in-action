package blog.in.action.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MaliciousController {

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/get")
    public String get() {
        return "get";
    }

    @GetMapping("/post")
    public String post() {
        return "post";
    }
}
