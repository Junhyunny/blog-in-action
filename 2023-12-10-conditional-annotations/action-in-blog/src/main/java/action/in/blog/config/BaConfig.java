package action.in.blog.config;

import action.in.blog.service.BarService;
import action.in.blog.service.BazService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "property", name = "ba-components", havingValue = "enabled")
@Configuration
public class BaConfig {

    @Bean
    public BarService barService() {
        return new BarService();
    }

    @Bean
    public BazService bazService() {
        return new BazService();
    }
}
