package action.in.blog.component;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

record HealthCheck(String status) {
}

@Component
public class ExternalServiceHealthIndicator implements HealthIndicator {

    private static final String EXTERNAL_SERVICE_URL = "http://external-service:8080/actuator/health";
    private static final String HEALTH_STATUS_UP = "UP";

    private final RestTemplate restTemplate;

    public ExternalServiceHealthIndicator() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public Health health() {
        try {
            var response = restTemplate.getForObject(EXTERNAL_SERVICE_URL, HealthCheck.class);
            if (response != null && HEALTH_STATUS_UP.equalsIgnoreCase(response.status())) {
                return Health.up().build();
            }
        } catch (Exception e) {
            return Health.down().build();
        }
        return Health.down().build();
    }
}
