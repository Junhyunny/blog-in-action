package action.in.blog.client;

import action.in.blog.domain.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "blog-client", url = "${server-url}")
public interface BlogClient {

    @GetMapping("/health")
    String health();

    @GetMapping("/search")
    String search(@RequestParam String keyword);

    @PostMapping("/post")
    Post createPost(@RequestBody Post post);
}
