package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class SessionController {

    private final String key = "key";

    @GetMapping("/session/creation")
    public void createSession(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException {
        HttpSession session = servletRequest.getSession();
        session.setAttribute(key, 0);
        servletResponse.sendRedirect("/session");
    }

    @GetMapping("/session")
    public String getSession(HttpSession session) throws IOException {
        int data = (int) session.getAttribute(key);
        session.setAttribute(key, data + 1);
        return "Current Data in Session - " + data;
    }
}
