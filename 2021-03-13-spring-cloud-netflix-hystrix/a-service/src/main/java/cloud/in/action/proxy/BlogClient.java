package cloud.in.action.proxy;

import feign.hystrix.FallbackFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "blog-client",
        url = "http://b-service:8080",
        fallbackFactory = BlogClientFallbackFactory.class
)
public interface BlogClient {

    @GetMapping(path = "/timeout")
    String requestWithTimeoutException();

    @GetMapping(path = "/exception")
    String requestWithIntentionalException();
}

@Log4j2
@Component
class BlogClientFallbackFactory implements FallbackFactory<BlogClient> {

    @Override
    public BlogClient create(Throwable cause) {
        log.error(cause.getMessage(), cause);
        return new BlogClientFallbackPlan();
    }

    class BlogClientFallbackPlan implements BlogClient {

        @Override
        public String requestWithTimeoutException() {
            return "timeout fallback";
        }

        @Override
        public String requestWithIntentionalException() {
            return "implicit exception fallback";
        }
    }
}