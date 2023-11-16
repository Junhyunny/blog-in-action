package action.in.blog.domain.model;

import action.in.blog.domain.entity.UserEntity;
import action.in.blog.domain.enums.LoginType;
import action.in.blog.domain.enums.Role;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class GoogleUser {

    private final OAuth2User oAuth2User;

    public GoogleUser(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .role(Role.USER)
                .email(email())
                .nickName(nickName())
                .loginType(LoginType.GOOGLE)
                .oauth2ClientId(oAuth2User.getName())
                .build();
    }

    private Map<String, Object> attributes() {
        return oAuth2User.getAttributes();
    }

    private String nickName() {
        return String.valueOf(attributes().get("name"));
    }

    private String email() {
        return String.valueOf(attributes().get("email"));
    }
}
