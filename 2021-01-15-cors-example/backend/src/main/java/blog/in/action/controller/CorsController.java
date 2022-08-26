package blog.in.action.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorsController {

    @GetMapping("/health")
    public String health() {
        return "It occurs CORS policy error.";
    }

    @CrossOrigin(origins = "http://localhost")
    @GetMapping("/cors-health")
    public String healthCorsAnnotation() {
        return "It's okay because of @CrossOrigin annotation.";
    }
}
