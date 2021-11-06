package blog.in.action.mysql.controller;

import blog.in.action.mysql.service.MySqlService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MySqlController {

    private final MySqlService service;

    public MySqlController(MySqlService service) {
        this.service = service;
    }

    @RequestMapping("/")
    public String index() {
        return "Hello. This is Junhyunny's blog";
    }

    @RequestMapping("/mysql")
    public @ResponseBody
    List<Map<String, Object>> selectTest() {
        return service.selectTest();
    }
}
