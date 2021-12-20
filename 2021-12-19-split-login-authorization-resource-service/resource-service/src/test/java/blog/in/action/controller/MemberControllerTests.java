package blog.in.action.controller;

import blog.in.action.ActionInBlogApplication;
import blog.in.action.entity.Member;
import blog.in.action.repository.MemberRepository;
import blog.in.action.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = ActionInBlogApplication.class)
@Import(AuthorizationServer.class)
public class MemberControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private MemberRepository memberRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void givenMemberId_whenWithoutToken_thenIsUnauthorized() throws Exception {

        mockMvc.perform(get("/member/Junhyunny"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenMemberId_whenWithBearerToken_thenIsOk() throws Exception {

        when(memberRepository.findById("Junhyunny"))
                .thenReturn(Optional.of(new Member("Junhyunny", "123", Collections.singletonList("ADMIN"))));

        mockMvc.perform(get("/member/Junhyunny")
                        .header("Authorization", "Bearer " + getAccessToken("Junhyunny", "123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("Junhyunny")))
                .andExpect(jsonPath("$.authorities").isArray())
                .andExpect(jsonPath("$.authorities").isNotEmpty())
                .andExpect(jsonPath("$.authorities[0]", is("ADMIN")));
    }

    @Test
    public void givenMemberId_whenUnsupportedAuthorization_thenIsForbidden() throws Exception {

        mockMvc.perform(get("/member/TestUser")
                        .header("Authorization", "Bearer " + getAccessToken("TestUser", "123")))
                .andExpect(status().isForbidden());
    }

    public String getAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("username", username);
        params.add("password", password);

        ResultActions result = mockMvc.perform(post("/oauth/token")
                        .params(params)
                        .with(httpBasic("CLIENT_ID", "CLIENT_SECRET"))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        Map<String, Object> parsedMap = jsonParser.parseMap(resultString);
        assertThat(parsedMap.get("access_token")).isNotNull();
        assertThat(parsedMap.get("refresh_token")).isNotNull();
        assertThat(parsedMap.get("token_type")).isEqualTo("bearer");
        return parsedMap.get("access_token").toString();
    }
}

@TestConfiguration
@RequiredArgsConstructor
@EnableAuthorizationServer
class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private String clientId = "CLIENT_ID";

    private String clientSecret = "CLIENT_SECRET";

    private int ACCESS_TOKEN_VALID_SECONDS = 10 * 60 * 24;

    private int REFRESH_TOKEN_VALID_SECONDS = 60 * 60 * 24;

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() throws Exception {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("TEMP_SIGN_KEY");
        converter.afterPropertiesSet();
        return converter;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(clientId)//
                .secret(passwordEncoder.encode(clientSecret))//
                .authorizedGrantTypes("password", "refresh_token")//
                .scopes("read")//
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALID_SECONDS)// token 유효 시간 등록
                .refreshTokenValiditySeconds(REFRESH_TOKEN_VALID_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter())); // JWT Converter 등록
        endpoints.userDetailsService(memberService)// UserDetailsService 등록
                .authenticationManager(authenticationManager)//
                .tokenEnhancer(tokenEnhancerChain);
    }
}