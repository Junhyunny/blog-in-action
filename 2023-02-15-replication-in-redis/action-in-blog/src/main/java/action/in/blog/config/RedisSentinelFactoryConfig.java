package action.in.blog.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;

@Profile("sentinel")
@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisSentinelFactoryConfig {

    @Value("#{'${redis-sentinel.hosts}'.split(',')}")
    private List<String> hosts;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration().master("mymaster");
        for (String host : hosts) {
            redisSentinelConfiguration.sentinel(host, 26379);
        }
        return new LettuceConnectionFactory(redisSentinelConfiguration);
    }
}