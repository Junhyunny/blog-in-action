package action.`in`.blog.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
class SessionConfig {
    @Bean
    fun springSessionDefaultRedisSerializer(): RedisSerializer<Any> = GenericJackson2JsonRedisSerializer()
}