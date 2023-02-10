package action.in.blog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "blog-client", url = "http://placeholder-service.com")
public interface BlogClient {

    @GetMapping("/health")
    String health(@RequestParam(name = "id") String id);
}
