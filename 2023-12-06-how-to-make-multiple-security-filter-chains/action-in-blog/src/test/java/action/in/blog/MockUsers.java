package action.in.blog;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class MockUsers {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService inMemoryUserDetailsManager() {
        var inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        inMemoryUserDetailsManager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("junhyunny")
                        .password("123")
                        .roles("ADMIN")
                        .build()
        );
        inMemoryUserDetailsManager.createUser(
                User.withDefaultPasswordEncoder()
                        .username("jua")
                        .password("123")
                        .roles("USER")
                        .build()
        );
        return inMemoryUserDetailsManager;
    }
}
