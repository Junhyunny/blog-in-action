package action.in.blog.domain.model;

import action.in.blog.domain.entity.UserEntity;
import action.in.blog.domain.enums.LoginType;
import action.in.blog.domain.enums.Role;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser {

    private final OAuth2User oAuth2User;

    public KakaoUser(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .role(Role.USER)
                .email(email())
                .nickName(nickName())
                .loginType(LoginType.KAKAO)
                .oauth2ClientId(oAuth2User.getName())
                .build();
    }

    private Map<String, Object> properties() {
        return oAuth2User.getAttribute("properties");
    }

    private Map<String, Object> account() {
        return oAuth2User.getAttribute("kakao_account");
    }

    private String nickName() {
        return String.valueOf(properties().get("nickname"));
    }

    private String email() {
        return String.valueOf(account().get("email"));
    }
}
