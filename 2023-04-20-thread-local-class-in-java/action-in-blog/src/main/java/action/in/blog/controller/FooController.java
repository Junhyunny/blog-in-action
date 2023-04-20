package action.in.blog.controller;

import action.in.blog.domain.AuthenticatedUser;
import action.in.blog.service.FooService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    private final FooService fooService;

    public FooController(FooService fooService) {
        this.fooService = fooService;
    }

    @PostMapping("/foo")
    public void createFoo(AuthenticatedUser user) {
        fooService.createFoo(user);
    }
}
