package blog.in.action.controller;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulController {

    @RequestMapping("/test")
    public String test(HttpServletRequest request, ServletResponse response) {
        request.getSession(true);
        return "test";
    }

}
