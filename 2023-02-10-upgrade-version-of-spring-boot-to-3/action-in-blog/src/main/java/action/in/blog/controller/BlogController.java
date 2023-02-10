package action.in.blog.controller;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BlogController {

    @GetMapping("/health")
    public String health(ServletRequest servletRequest, ServletResponse servletResponse) {
        log.info("health");
        return "OK";
    }
}
