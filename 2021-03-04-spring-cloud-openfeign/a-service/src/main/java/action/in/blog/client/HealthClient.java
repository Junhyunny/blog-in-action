package action.in.blog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "health-client", url = "http://b-service:8080")
public interface HealthClient {

    @GetMapping(path = "/health")
    String health();
}
