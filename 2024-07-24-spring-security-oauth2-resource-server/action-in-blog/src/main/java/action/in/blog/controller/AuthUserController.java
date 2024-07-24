package action.in.blog.controller;

import action.in.blog.domain.UserResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api/users")
public class AuthUserController {

    @PostMapping("/me")
    public UserResponse userMe(@AuthenticationPrincipal Jwt principal) {
        return new UserResponse(
                1,
                principal.getClaim("name"),
                ""
        );
    }
}
