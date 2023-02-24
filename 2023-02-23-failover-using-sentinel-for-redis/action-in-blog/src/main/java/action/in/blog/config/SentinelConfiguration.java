package action.in.blog.config;

import io.lettuce.core.models.role.RedisInstance;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
class SentinelInstance {

    private String host;
    private int port;
}

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "redis")
public class SentinelConfiguration {

    private List<SentinelInstance> sentinels;
}