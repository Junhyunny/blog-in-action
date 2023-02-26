package action.in.blog.client;

import action.in.blog.domain.BlogQuery;
import action.in.blog.domain.BlogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "blog-client", url = "${blog-server.url}")
public interface BlogClient {

    @GetMapping("/search")
    List<BlogResponse> getBlogResponsesWithParams(
            @RequestParam int age,
            @RequestParam String name,
            @RequestParam String address
    );

    @GetMapping("/search")
    List<BlogResponse> getBlogResponsesWithDto(@SpringQueryMap BlogQuery query);

}
