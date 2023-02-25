package action.in.blog.client;

import action.in.blog.domain.BlogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "blog-client", url = "${blog-server.url}")
public interface BlogClient {

    @GetMapping("/search")
    BlogResponse getBlogResponse(@RequestParam("key") String key);
}
