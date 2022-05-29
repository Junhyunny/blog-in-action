package blog.in.action.client;

import blog.in.action.annotation.InterfaceMeta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "simple-client", url = "https://junhyunny.github.io")
public interface SimpleClient {

    @InterfaceMeta(explainText = "블로그 홈", serviceId = "0001")
    @GetMapping(path = "/")
    String home();

    @InterfaceMeta(explainText = "자기소개", serviceId = "0002")
    @GetMapping(path = "/about/")
    String about();
}
