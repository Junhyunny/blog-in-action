package app.auth.config;

import app.auth.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean setFilterRegistration() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new SessionFilter("hello world"));
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
