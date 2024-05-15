package action.in.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.data.redis.RedisHealthIndicator;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class AppConfig {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port
    ) {
        var clientConfig = LettuceClientConfiguration
                .builder()
                .commandTimeout(Duration.ofMillis(5000L))
                .build();
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host, port), clientConfig);
    }

    @Bean
    public HealthIndicator redisHealthIndicator(RedisConnectionFactory connectionFactory) {
        return new RedisHealthIndicator(connectionFactory);
    }
}
