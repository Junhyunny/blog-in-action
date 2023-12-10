package action.in.blog.config;

import action.in.blog.service.FooService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FooConfig {

    @ConditionalOnProperty(
            prefix = "property",
            value = "foo",
            havingValue = "enabled"
    )
    @Bean
    public FooService fooService() {
        return new FooService("foo.enabled");
    }

    @ConditionalOnProperty(
            prefix = "property",
            value = "foo",
            havingValue = "disabled",
            matchIfMissing = true
    )
    @Bean
    public FooService disabledFooService() {
        return new FooService("foo.disabled");
    }
}
