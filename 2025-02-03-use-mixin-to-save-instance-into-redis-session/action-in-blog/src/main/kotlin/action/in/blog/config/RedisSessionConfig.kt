package action.`in`.blog.config

import action.`in`.blog.controller.Address
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.security.jackson2.SecurityJackson2Modules
import java.util.*

class CustomAppMixin // 1

@Configuration
class RedisSessionConfig {
    @Bean
    fun springSessionDefaultRedisSerializer(): RedisSerializer<Any> = GenericJackson2JsonRedisSerializer(objectMapper())

    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        val modules = SecurityJackson2Modules.getModules(javaClass.classLoader)
        objectMapper.registerModules(modules)
        objectMapper.addMixIn(UUID::class.java, CustomAppMixin::class.java) // 2
        objectMapper.addMixIn(Address::class.java, CustomAppMixin::class.java)
        return objectMapper
    }
}
