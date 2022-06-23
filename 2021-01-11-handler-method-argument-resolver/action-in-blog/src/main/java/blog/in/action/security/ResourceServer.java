package blog.in.action.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.cors().and() //
				.authorizeRequests() //
				.antMatchers("/api/member/sign-up").permitAll() // sign-up API는 모든 요청 허용
				.antMatchers("/api/member/user-info").hasAnyAuthority("ADMIN")// user-info API는 ADMIN 권한을 가지는 유저만 요청 허용
				.anyRequest().authenticated().and() //
				.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
	}
}