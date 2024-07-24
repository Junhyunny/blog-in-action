package action.in.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {
        http.authorizeHttpRequests(
                registry ->
                        registry.anyRequest().authenticated()
        );
        http.oauth2ResourceServer(
                configurer ->
                        configurer.jwt(
                                Customizer.withDefaults()
                        )
        );
        return http.build();
    }
}
