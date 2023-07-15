package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogController {

    @GetMapping("/foo")
    public String foo() {
        return "Hello Foo World";
    }

    @GetMapping("/bar")
    public String bar() {
        return "Hello Bar World";
    }
}
