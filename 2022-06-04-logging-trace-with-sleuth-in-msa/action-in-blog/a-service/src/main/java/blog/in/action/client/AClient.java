package blog.in.action.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient
public interface AClient {

    @GetMapping("b-path")
    String getDataFromBService();
}
