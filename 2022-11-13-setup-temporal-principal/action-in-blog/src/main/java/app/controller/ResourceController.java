package app.controller;

import app.auth.AuthenticatedUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/user")
    public AuthenticatedUser getUser(AuthenticatedUser user) {
        return user;
    }
}
