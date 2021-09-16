package blog.in.action.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PcController {

    @RequestMapping
    public String index() {
        return "/index";
    }
}
