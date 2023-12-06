package action.in.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] adminSecurityMatcher = new String[]{
            "/admin/api/**",
            "/admin/login"
    };

    private static final String[] appSecurityMatcher = new String[]{
            "/app/api/**",
            "/app/login"
    };

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher(adminSecurityMatcher);
        httpSecurity.authorizeHttpRequests(
                registry -> registry.anyRequest().hasRole("ADMIN")
        );
        httpSecurity.formLogin(
                configurer -> configurer
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/home")
                        .failureUrl("/admin/login?error")
        );
        httpSecurity.csrf(
                AbstractHttpConfigurer::disable
        );
        return httpSecurity.build();
    }

    @Bean
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher(appSecurityMatcher);
        httpSecurity.authorizeHttpRequests(
                registry -> registry
                        .requestMatchers(HttpMethod.GET, "/app/api/private-articles").authenticated()
                        .anyRequest().permitAll()
        );
        httpSecurity.formLogin(
                configurer -> configurer
                        .loginProcessingUrl("/app/login")
                        .defaultSuccessUrl("/app/home")
                        .failureUrl("/app/login?error")
        );
        httpSecurity.csrf(
                AbstractHttpConfigurer::disable
        );
        return httpSecurity.build();
    }
}
