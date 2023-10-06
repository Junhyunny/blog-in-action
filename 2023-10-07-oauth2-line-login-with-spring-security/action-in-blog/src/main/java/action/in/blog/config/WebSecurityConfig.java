package action.in.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.oauth2Login(
                configurer -> configurer
                        .defaultSuccessUrl("/home")
                        .redirectionEndpoint(
                                config -> config.baseUri("/oauth2/authorized/line")
                        )
        ).authorizeHttpRequests(
                registry -> registry
                        .requestMatchers("/home")
                        .authenticated()
        );
        return http.build();
    }
}