package action.in.blog.filter;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("auth-filter")
@RestController
public class AuthFilterTestController {

    @GetMapping("/foo")
    public String foo() {
        return "Hello Foo World";
    }

    @GetMapping("/bar")
    public String bar() {
        return "Hello Bar World";
    }
}
