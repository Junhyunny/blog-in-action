package action.`in`.blog.backend.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Profile(value = ["pcf"])
@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "https://todos-frontend.apps.sandbox.aws.maki.lol",
                "https://todos-frontend.apps.dhaka.cf-app.com"
            )
            .allowedMethods("GET", "POST", "OPTIONS")
    }
}