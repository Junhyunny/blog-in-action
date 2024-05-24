package action.in.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient(
            OAuth2AuthorizationCodeGrantRequestEntityConverter oAuth2AuthorizationCodeGrantRequestEntityConverter
    ) {
        var client = new DefaultAuthorizationCodeTokenResponseClient();
        client.setRequestEntityConverter(oAuth2AuthorizationCodeGrantRequestEntityConverter);
        return client;
    }

    @Bean
    public OidcAuthorizationCodeAuthenticationProvider auth2AuthorizationCodeAuthenticationProvider(
            OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient
    ) {
        return new OidcAuthorizationCodeAuthenticationProvider(accessTokenResponseClient, new OidcUserService());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(
                        configurer -> configurer.anyRequest().authenticated()
                )
                .oauth2Login(
                        configurer -> configurer.defaultSuccessUrl("/main")
                )
                .csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }
}
