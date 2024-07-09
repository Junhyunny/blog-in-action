package action.in.blog.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    @GetMapping
    public String index(HttpServletResponse response) {
        response.addHeader("Set-Cookie", "DefaultCookie=DefaultCookie");
        response.addHeader("Set-Cookie", "LaxCookie=LaxCookie;SameSite=Lax");
        return "index";
    }

    @PostMapping("/posts")
    public String posts() {
        return "index";
    }
}
