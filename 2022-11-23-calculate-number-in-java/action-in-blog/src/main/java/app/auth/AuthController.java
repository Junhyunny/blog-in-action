package app.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AuthController {

    @GetMapping("/test-1")
    public AuthUser ssoAuthentication(AuthUser authUser) throws IOException {
        return authUser;
    }
//    private final AuthManager authManager;
//    private final String authServerUrl;
//
//    public AuthController(AuthManager authManager, String authServerUrl) {
//        this.authManager = authManager;
//        this.authServerUrl = authServerUrl;
//    }
//
//    @GetMapping("/sso/auth")
//    public void ssoAuthentication(HttpServletResponse servletResponse) throws IOException {
//        servletResponse.sendRedirect(authServerUrl);
//    }
//
//    @GetMapping("/sso/check")
//    public void getUserByAuthToken(HttpServletResponse servletResponse, @RequestParam String authToken) throws IOException {
//        AuthUser authUser = authManager.getUserByToken(authToken);
//        servletResponse.sendRedirect(authUser.getReturnUrl());
//    }
}
