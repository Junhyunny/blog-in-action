package action.in.blog.service;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultOAuth2UserServiceDelegator extends DefaultOAuth2UserService {

    private final List<CustomOAuth2UserService> oAuth2UserServices;


    public DefaultOAuth2UserServiceDelegator(List<CustomOAuth2UserService> oAuth2UserServices) {
        this.oAuth2UserServices = oAuth2UserServices;
    }

    public OAuth2User loadUserFromParent(OAuth2UserRequest userRequest) {
        return super.loadUser(userRequest);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        for (var oauth2Service : oAuth2UserServices) {
            if (!oauth2Service.supports(userRequest)) {
                continue;
            }
            var oauthUser = loadUserFromParent(userRequest);
            return oauth2Service.createOrLoadUser(oauthUser);
        }
        throw new RuntimeException("Not found user service");
    }
}
