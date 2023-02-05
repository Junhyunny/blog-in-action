package cloud.in.action.controller;

import cloud.in.action.domain.Health;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final Environment environment;

    public HealthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/health")
    public Health health() {
        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        return Health.builder()
                .serviceName("SERVICE-B")
                .port(port)
                .status("OK")
                .build();
    }
}