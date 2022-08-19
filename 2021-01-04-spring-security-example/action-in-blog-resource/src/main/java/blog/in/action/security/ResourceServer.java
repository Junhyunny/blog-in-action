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
        // 토큰 정보를 다루기 위한 토큰 서비스 객체 생성
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        // JWT 토큰 변경을 위한 컨버터 생성
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // JWT 토큰 인코딩과 디코딩에 사용되므로 인증 서버와 동일한 암호키를 사용합니다. - TEMP_SIGN_KEY
        converter.setSigningKey("TEMP_SIGN_KEY");
        converter.afterPropertiesSet();
        // JWT 토큰 컨버터와 JWT 토큰 스토어 등록
        defaultTokenServices.setTokenStore(new JwtTokenStore(converter));
        defaultTokenServices.setSupportRefreshToken(true);
        // 토큰 서비스 등록
        resources.tokenServices(defaultTokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 권한 확인이 필요한 요청 정보 등록
                .authorizeRequests()
                // /h2-console/** 경로는 모든 요청에 대해 허용
                .antMatchers("/h2-console/**").permitAll()
                // /member/user-info 경로는 ADMIN만 접근 가능
                .antMatchers("/member/user-info").hasAnyAuthority("USER")
                // 나머지 요청은 인증만 필요
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new OAuth2AccessDeniedHandler());
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}