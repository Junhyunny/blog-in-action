package action.in.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class TestController {

    @GetMapping("/will-redirect")
    public void willRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/redirected");
    }

    @GetMapping("/redirected")
    public String redirected() {
        return "redirected";
    }

    @GetMapping("/will-forward")
    public void willForward(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getRequestDispatcher("/forwarded").forward(request, response);
    }

    @GetMapping("/forwarded")
    public String forwarded() {
        return "forwarded";
    }
}
