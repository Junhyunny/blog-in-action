package action.in.blog.config;

import action.in.blog.filter.BarFilter;
import action.in.blog.filter.FooFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<FooFilter> fooFilterRegistrationBean() {
        FilterRegistrationBean<FooFilter> registrationBean = new FilterRegistrationBean<>(new FooFilter());
        registrationBean.addUrlPatterns("/foo");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<BarFilter> barFilterRegistrationBean() {
        FilterRegistrationBean<BarFilter> registrationBean = new FilterRegistrationBean<>(new BarFilter());
        registrationBean.addUrlPatterns("/bar");
        return registrationBean;
    }
}
