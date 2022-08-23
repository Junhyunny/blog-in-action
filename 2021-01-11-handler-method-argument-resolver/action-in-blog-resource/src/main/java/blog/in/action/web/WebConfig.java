package blog.in.action.web;

import blog.in.action.resolvers.AuthHandlerMethodArgumentResolver;
import blog.in.action.resolvers.LocalDateHandlerMethodArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthHandlerMethodArgumentResolver authHandlerMethodArgumentResolver;

    private final LocalDateHandlerMethodArgumentResolver localDateHandlerMethodArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.addAll(Arrays.asList(authHandlerMethodArgumentResolver, localDateHandlerMethodArgumentResolver));
    }
}
