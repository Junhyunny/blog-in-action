package blog.in.action.config;

import blog.in.action.handler.AuthenticationInterceptor;
import blog.in.action.handler.DoubleSubmitCookieInterceptor;
import blog.in.action.handler.ReferrerCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).excludePathPatterns("", "/", "/login").addPathPatterns("/**");
        registry.addInterceptor(new ReferrerCheckInterceptor()).excludePathPatterns("", "/", "/login").addPathPatterns("/**");
        // registry.addInterceptor(new CsrfTokenInterceptor()).addPathPatterns("/change/**");
        registry.addInterceptor(new DoubleSubmitCookieInterceptor()).addPathPatterns("/change/**");
    }
}