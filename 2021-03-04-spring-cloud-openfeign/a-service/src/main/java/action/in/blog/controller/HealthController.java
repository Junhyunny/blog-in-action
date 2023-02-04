package action.in.blog.controller;

import action.in.blog.client.HealthClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final HealthClient healthClient;

    public HealthController(HealthClient healthClient) {
        this.healthClient = healthClient;
    }

    @GetMapping("/health")
    public String health() {
        return String.format("ServiceA's Health - OK / ServiceB's Health - %s", healthClient.health());
    }
}
