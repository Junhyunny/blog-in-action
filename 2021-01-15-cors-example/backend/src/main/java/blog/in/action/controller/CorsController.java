package blog.in.action.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/cors")
public class CorsController {

	@GetMapping("/health")
	public String health() {
		return "health";
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/health-cors-annotaion")
	public String healthCorsAnnotation() {
		return "health-cors-annotaion";
	}
}
