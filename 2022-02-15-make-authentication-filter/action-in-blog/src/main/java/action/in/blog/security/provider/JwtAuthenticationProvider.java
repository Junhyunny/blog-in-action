package action.in.blog.security.provider;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.warn("implement later");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        log.warn("implement later");
        return false;
    }
}
