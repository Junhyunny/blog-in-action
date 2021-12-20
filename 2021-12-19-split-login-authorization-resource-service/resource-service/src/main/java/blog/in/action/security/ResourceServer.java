package blog.in.action.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("TEMP_SIGN_KEY");
        converter.afterPropertiesSet();
        defaultTokenServices.setTokenStore(new JwtTokenStore(converter));
        defaultTokenServices.setSupportRefreshToken(true);
        resources.tokenServices(defaultTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and() //
                .authorizeRequests() //
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/member/**").hasAnyAuthority("ADMIN") // member API는 ADMIN 권한을 가지는 유저만 요청 허용
                .anyRequest().authenticated()
                .and() //
                .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        // http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}