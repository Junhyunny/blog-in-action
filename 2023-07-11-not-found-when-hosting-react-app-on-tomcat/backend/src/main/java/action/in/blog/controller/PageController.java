package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PageController {

    @GetMapping("/page-first")
    public String pageFirst() {
        return "This is first page";
    }

    @GetMapping("/page-second")
    public String pageSecond() {
        return "This is second page";
    }
}
