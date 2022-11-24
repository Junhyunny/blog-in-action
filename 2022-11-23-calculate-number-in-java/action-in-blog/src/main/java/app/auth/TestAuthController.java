package app.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class TestAuthController {

    @GetMapping("/test-2")
    public AuthUser ssoAuthentication(HttpServletRequest request, HttpServletResponse servletResponse) throws IOException {
//        Cookie cookie = new Cookie("custom-session-id", "World");
//        servletResponse.addCookie(cookie);

        ObjectMapper objectMapper = new ObjectMapper();

        AuthUser user =  AuthUser.builder()
                .id("1234")
                .returnUrl("123123312312")
                .name("Junhyunny")
                .build();
        HttpSession session = request.getSession();
        session.setAttribute("some-token", objectMapper.writeValueAsString(user));
        return user;
    }
}
