package action.in.blog.client;

import action.in.blog.config.BlogClientConfig;
import action.in.blog.domain.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "blog-client", url = "${server-url}", configuration = {BlogClientConfig.class})
public interface BlogClient {

    @GetMapping("/posts")
    List<Post> getPosts();
}
