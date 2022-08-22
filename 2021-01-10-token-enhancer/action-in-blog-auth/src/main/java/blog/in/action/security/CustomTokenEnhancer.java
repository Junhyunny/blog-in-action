package blog.in.action.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User user = (User) oAuth2Authentication.getPrincipal();
        Map<String, Object> additionalInfo = new LinkedHashMap<>(oAuth2AccessToken.getAdditionalInformation());
        additionalInfo.put("MEMBER_ID", user.getUsername());
        additionalInfo.put("SERVICE-SECRET-KEY", "JUNHYUNNY AUTH SERVICE");
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
