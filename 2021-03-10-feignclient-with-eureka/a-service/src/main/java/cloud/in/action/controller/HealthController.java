package cloud.in.action.controller;

import cloud.in.action.client.HealthClient;
import cloud.in.action.domain.Health;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class HealthController {

    private final Environment environment;
    private final HealthClient healthClient;

    public HealthController(Environment environment, HealthClient healthClient) {
        this.environment = environment;
        this.healthClient = healthClient;
    }

    @GetMapping("/health")
    public List<Health> health() {
        int port = Integer.parseInt(environment.getProperty("local.server.port"));
        Health healthOfServiceA = Health.builder()
                .serviceName("SERVICE-A")
                .port(port)
                .status("OK")
                .build();
        Health healthOfServiceB = healthClient.health();
        return Arrays.asList(healthOfServiceA, healthOfServiceB);
    }
}