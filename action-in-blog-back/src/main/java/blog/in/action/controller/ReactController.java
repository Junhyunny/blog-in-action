package blog.in.action.controller;

import java.util.Random;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ReactController {

    @GetMapping("/index")
    public String index() {
        if(new Random().nextBoolean()) {
            return "success";
        }
        return "fail";
    }
}
