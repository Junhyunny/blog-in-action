package action.in.blog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@FeignClient(name = "health-client", url = "http://placeholder-url.com")
public interface HealthClient {

    @GetMapping(path = "/health")
    String health(URI baseUri);
}
