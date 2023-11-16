package action.in.blog.domain.token;

import action.in.blog.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticatedUser implements Principal, OAuth2User {

    private String name;
    private List<String> roles;
    private String email;
    private String nickName;
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public static AuthenticatedUser of(UserEntity user, OAuth2User oauth2User) {
        return AuthenticatedUser.builder()
                .name(String.valueOf(user.getId()))
                .email(user.getEmail())
                .roles(List.of(user.getRole().name()))
                .nickName(user.getNickName())
                .attributes(oauth2User.getAttributes())
                .build();
    }
}
