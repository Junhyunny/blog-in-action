package blog.in.action.postgresql.controller;

import blog.in.action.postgresql.service.PostgreSqlService;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostgreSqlController {

    private final PostgreSqlService service;

    public PostgreSqlController(PostgreSqlService service) {
        this.service = service;
    }

    @RequestMapping("/postgresql")
    public @ResponseBody
    List<Map<String, Object>> selectTest() {
        return service.selectTest();
    }
}
