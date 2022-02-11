package action.in.blog.config;

import action.in.blog.filters.CustomFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new CustomFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD));
        return registrationBean;
    }
}
