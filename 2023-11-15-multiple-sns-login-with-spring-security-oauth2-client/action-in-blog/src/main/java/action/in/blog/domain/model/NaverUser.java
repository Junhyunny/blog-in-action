package action.in.blog.domain.model;

import action.in.blog.domain.entity.UserEntity;
import action.in.blog.domain.enums.LoginType;
import action.in.blog.domain.enums.Role;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class NaverUser {

    private final OAuth2User oAuth2User;

    public NaverUser(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public UserEntity toUserEntity() {
        return UserEntity.builder()
                .role(Role.USER)
                .email(email())
                .nickName(nickName())
                .loginType(LoginType.NAVER)
                .oauth2ClientId(subject())
                .build();
    }

    private Map<String, Object> response() {
        return oAuth2User.getAttribute("response");
    }

    public String subject() {
        return String.valueOf(response().get("id"));
    }

    private String nickName() {
        return String.valueOf(response().get("name"));
    }

    private String email() {
        return String.valueOf(response().get("email"));
    }
}
