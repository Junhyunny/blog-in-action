package blog.in.action.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    @RequestMapping(path = {"", "/"})
    public String index() {
        return "Hello, this is Junhyunny's blog.";
    }
}
