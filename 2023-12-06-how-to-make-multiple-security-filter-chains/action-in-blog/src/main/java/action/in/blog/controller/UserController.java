package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/api")
public class UserController {

    @GetMapping("/articles")
    public String articles() {
        return "user articles";
    }

    @GetMapping("/private-articles")
    public String privateArticles() {
        return "user's private articles";
    }

}
