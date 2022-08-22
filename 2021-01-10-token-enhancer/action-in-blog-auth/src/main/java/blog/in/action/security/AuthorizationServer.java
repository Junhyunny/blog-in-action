package blog.in.action.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private String clientId = "CLIENT_ID";

    private String clientSecret = "CLIENT_SECRET";

    private int ACCESS_TOKEN_VALID_SECONDS = 10 * 60 * 24;

    private int REFRESH_TOKEN_VALID_SECONDS = 60 * 60 * 24;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final CustomTokenEnhancer customTokenEnhancer;

    private final JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 해당 인증 서버를 이용하는 클라이언트 어플리케이션 정보를 추가합니다.
        clients
                // 인증 서버 메모리에 추가합니다.
                .inMemory()
                // 클라이언트 어플리케이션에 미리 발급된 ID
                .withClient(clientId)
                // 클라이언트 어플리케이션에 미리 발급된 SECRETE, 암호화하여 추가
                .secret(passwordEncoder.encode(clientSecret))
                // 인증 방법은 비밀번호와 리프레시 토큰
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("read")
                // access token 유효 시간 등록
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALID_SECONDS)
                // refresh token 유효 시간 등록
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALID_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // JSON WEB TOKEN 을 사용하기 위한 컨버터 등록
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, jwtAccessTokenConverter));
        endpoints
                // Spring Security 프레임워크에서 사용하는 AuthenticationManager 등록
                .authenticationManager(authenticationManager)
                // 토큰 강화를 위한 TokenEnhancer 등록
                .tokenEnhancer(tokenEnhancerChain);
    }
}