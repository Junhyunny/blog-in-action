package action.in.blog;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
public class CookieController {

    @GetMapping("/cookies")
    public List<String> getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(cookie -> String.join("=", cookie.getName(), cookie.getValue()))
                    .toList();
        }
        return Collections.emptyList();
    }
}
