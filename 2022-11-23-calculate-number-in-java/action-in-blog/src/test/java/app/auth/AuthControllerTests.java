package app.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.clearAllCaches;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class AuthControllerTests {

    String authServerUrl = "http://auth-server/sso/auth";
    String authToken = "access_token_from_auth_server";
    String businessMainUrl = "http://my-server/index.html";
    AuthManager authManager = Mockito.mock(AuthManager.class);

    @BeforeEach
    void setUp() {
        clearAllCaches();
    }

    @Test
    @DisplayName("리소스 요청 시 세션이 없는 사용자는 /sso/auth 경로로 리다이렉트 된다.")
    void when_request_resource_without_session_then_redirect_to_authentication_path() throws Exception {

        MockMvc sut = standaloneSetup(new AuthController())
                .addFilter(new SessionFilter("/sso/auth"))
                .build();


        sut.perform(get("/any-url"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sso/auth"));
    }

    @Test
    @DisplayName("/sso/auth 경로에 해당하는 요청은 인증 서버로 리다이렉트 한다.")
    void when_request_authentication_path_then_redirect_to_auth_server() throws Exception {

        MockMvc sut = standaloneSetup(new AuthController())
                .build();


        sut.perform(get("/sso/auth"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(authServerUrl));
    }

    @Test
    @DisplayName("리다이렉트 되어 돌아온 유효한 인증 토큰을 통해 사용자 정보를 획득하고, 비즈니스 시작 URL 로 리다이렉트할 수 있다.")
    void when_redirect_request_with_token_then_verify_token_through_auth_server() throws Exception {

        when(authManager.getUserByToken(authToken)).thenReturn(
                AuthUser.builder()
                        .id("Junhyunny")
                        .name("강준현")
                        .returnUrl(businessMainUrl)
                        .build()
        );
        MockMvc sut = standaloneSetup(new AuthController())
                .build();


        sut.perform(get("/sso/check")
                        .param("authToken", authToken))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(businessMainUrl));
    }

    @Test
    @DisplayName("토큰을 통해 사용자 인증을 받으면 세션을 생성하고, 사용자 정보를 세션에 저장할 수 있다.")
    void when_get_user_by_token_then_store_user_info_into_session() throws Exception {

        when(authManager.getUserByToken(authToken)).thenReturn(
                        AuthUser.builder()
                                .id("Junhyunny")
                                .name("강준현")
                                .returnUrl(businessMainUrl)
                                .build()
        );
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockMvc sut = standaloneSetup(new AuthController())
                .build();


        sut.perform(get("/sso/check")
                        .param("authToken", authToken));
    }
}