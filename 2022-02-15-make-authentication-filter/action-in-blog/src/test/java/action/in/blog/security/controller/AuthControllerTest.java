package action.in.blog.security.controller;

import action.in.blog.security.provider.JwtAuthenticationProvider;
import action.in.blog.security.tokens.JwtAuthenticationToken;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.ProviderManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProviderManagerBuilder providerManagerBuilder;

    @Test
    public void givenWithOutToken_whenCallLogin_thenIsOk() throws Exception {

        mockMvc.perform(
                        post("/auth/login")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void givenWithoutToken_whenCallNotExistsPath_thenIsForbidden() throws Exception {

        mockMvc.perform(
                        post("/something-other")
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenWithValidTokenAndSuccessAuthentication_whenCallNotExistsPath_thenNotFound() throws Exception {

        // setup
        Authentication authentication = new JwtAuthenticationToken("valid_token");
        Authentication authenticatedToken = new JwtAuthenticationToken("Junhyunny",
                "",
                Collections.singletonList((GrantedAuthority) () -> "ROLE_ADMIN")
        );

        JwtAuthenticationProvider provider = Mockito.mock(JwtAuthenticationProvider.class);
        when(provider.authenticate(authentication)).thenReturn(authenticatedToken);
        when(provider.supports(authentication.getClass())).thenReturn(true);
        providerManagerBuilder.authenticationProvider(provider);

        // action, then
        mockMvc.perform(
                        post("/something-other")
                                .header("Authorization", "Bearer valid_token")
                )
                .andExpect(status().isNotFound());
    }

}
