package action.in.blog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("config")
@Getter
@Setter
@Configuration
@ConfigurationProperties("redis")
public class SentinelConfiguration {

    private List<SentinelInstance> sentinels;

    @Getter
    @Setter
    public static class SentinelInstance {

        private String host;
        private int port;
    }
}
