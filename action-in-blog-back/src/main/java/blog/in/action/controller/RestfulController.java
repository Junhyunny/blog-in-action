package blog.in.action.controller;

import com.google.gson.GsonBuilder;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestfulController {

    @RequestMapping("/rest")
    public String rest(ServletRequest request) {
        return new GsonBuilder().setPrettyPrinting().create().toJson(((HttpServletRequest) request).getSession());
    }
}
