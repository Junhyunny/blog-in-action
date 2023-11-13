package action.in.blog.service;

import action.in.blog.domain.CustomAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DefaultOAuth2UserServiceDelegatorTest {

    CustomOAuth2UserService googleUserService;
    CustomOAuth2UserService facebookUserService;
    DefaultOAuth2UserServiceDelegator sut;

    @BeforeEach
    void setUp() {
        googleUserService = mock(CustomOAuth2UserService.class);
        facebookUserService = mock(CustomOAuth2UserService.class);
        sut = spy(new DefaultOAuth2UserServiceDelegator(
                List.of(googleUserService, facebookUserService)
        ));
    }

    @Test
    void loadUser() {

        var authentication = new CustomAuthentication();
        var oauth2UserRequest = mock(OAuth2UserRequest.class);
        var oauth2User = new DefaultOAuth2User(Collections.emptyList(), Map.of("id", "junhyunny"), "id");
        doReturn(oauth2User).when(sut).loadUserFromParent(oauth2UserRequest);
        when(googleUserService.supports(oauth2UserRequest)).thenReturn(true);
        when(googleUserService.createOrLoadUser(oauth2User)).thenReturn(authentication);


        var result = sut.loadUser(oauth2UserRequest);


        assertEquals(result, authentication);
    }
}