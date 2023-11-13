package action.in.blog.service;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomOAuth2UserService {

    boolean supports(OAuth2UserRequest userRequest);

    OAuth2User createOrLoadUser(OAuth2User oAuth2User);
}
