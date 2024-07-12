package action.in.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ActionInBlogApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }

    // 정상적으로 배포되지 않는 경우 해당 주석을 풀어보길 바란다.
    // @Override
    // protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    //     return builder.sources(ActionInBlogApplication.class);
    // }
}