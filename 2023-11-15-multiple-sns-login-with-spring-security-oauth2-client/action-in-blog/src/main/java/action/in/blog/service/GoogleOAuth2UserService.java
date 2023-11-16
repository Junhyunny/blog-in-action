package action.in.blog.service;

import action.in.blog.domain.entity.UserEntity;
import action.in.blog.domain.enums.LoginType;
import action.in.blog.domain.model.GoogleUser;
import action.in.blog.domain.token.AuthenticatedUser;
import action.in.blog.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GoogleOAuth2UserService implements CustomOAuth2UserService {

    private static final String REGISTRATION_ID = "google";
    private final UserRepository repository;

    public GoogleOAuth2UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(OAuth2UserRequest userRequest) {
        return REGISTRATION_ID.equals(userRequest.getClientRegistration().getRegistrationId());
    }

    @Transactional
    @Override
    public AuthenticatedUser createOrLoadUser(OAuth2User authenticatedUser) {
        var subject = authenticatedUser.getName();
        var optional = repository.findUserByOauth2ClientIdAndLoginType(subject, LoginType.GOOGLE);
        UserEntity user;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            user = new GoogleUser(authenticatedUser).toUserEntity();
            repository.save(user);
        }
        return AuthenticatedUser.of(user, authenticatedUser);
    }
}
