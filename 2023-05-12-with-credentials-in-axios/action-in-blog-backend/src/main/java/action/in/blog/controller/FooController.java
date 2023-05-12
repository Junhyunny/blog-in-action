package action.in.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    private static final String key = "foo";

    @GetMapping("/foo")
    public String foo(HttpServletRequest request) {
        String defaultValue = "foo";

        HttpSession session = request.getSession();
        String fooInSession = (String) session.getAttribute(key);
        if (fooInSession == null) {
            session.setAttribute(key, defaultValue);
            return defaultValue;
        }

        String result = fooInSession.concat("-").concat(defaultValue);
        session.setAttribute(key, result);
        return result;
    }
}
