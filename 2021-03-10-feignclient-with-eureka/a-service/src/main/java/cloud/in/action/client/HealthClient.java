package cloud.in.action.client;

import cloud.in.action.domain.Health;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "b-service-as-client")
public interface HealthClient {

    @GetMapping(path = "/health")
    Health health();
}