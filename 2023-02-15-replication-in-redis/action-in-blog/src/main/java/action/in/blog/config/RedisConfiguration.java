package action.in.blog.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
class RedisInstance {

    private String host;
    private int port;
}

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisConfiguration {

    private RedisInstance master;
    private List<RedisInstance> slaves;
}