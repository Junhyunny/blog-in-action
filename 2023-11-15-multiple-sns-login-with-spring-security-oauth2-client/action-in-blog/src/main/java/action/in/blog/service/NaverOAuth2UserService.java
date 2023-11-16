package action.in.blog.service;

import action.in.blog.domain.entity.UserEntity;
import action.in.blog.domain.enums.LoginType;
import action.in.blog.domain.token.AuthenticatedUser;
import action.in.blog.domain.model.NaverUser;
import action.in.blog.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NaverOAuth2UserService implements CustomOAuth2UserService {

    private static final String REGISTRATION_ID = "naver";
    private final UserRepository repository;

    public NaverOAuth2UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(OAuth2UserRequest userRequest) {
        return REGISTRATION_ID.equals(userRequest.getClientRegistration().getRegistrationId());
    }

    @Transactional
    @Override
    public AuthenticatedUser createOrLoadUser(OAuth2User authenticatedUser) {
        var naverUser = new NaverUser(authenticatedUser);
        var optional = repository.findUserByOauth2ClientIdAndLoginType(naverUser.subject(), LoginType.NAVER);
        UserEntity user;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            user = naverUser.toUserEntity();
            repository.save(user);
        }
        return AuthenticatedUser.of(user, authenticatedUser);
    }
}
