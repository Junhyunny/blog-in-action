package action.in.blog.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DelegatingOAuth2UserService extends DefaultOAuth2UserService {

    private final List<CustomOAuth2UserService> oauth2UserServices;

    public DelegatingOAuth2UserService(List<CustomOAuth2UserService> oauth2UserServices) {
        this.oauth2UserServices = oauth2UserServices;
    }

    public OAuth2User loadUserFromParent(OAuth2UserRequest userRequest) {
        return super.loadUser(userRequest);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        for (var oauth2Service : oauth2UserServices) {
            if (!oauth2Service.supports(userRequest)) {
                continue;
            }
            var oauth2User = loadUserFromParent(userRequest);
            return oauth2Service.createOrLoadUser(oauth2User);
        }
        throw new RuntimeException("지원하지 않는 플랫폼입니다.");
    }
}
