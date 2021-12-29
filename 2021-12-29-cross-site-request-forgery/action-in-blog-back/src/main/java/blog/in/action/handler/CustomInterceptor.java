package blog.in.action.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CustomInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("member") == null) {
            response.sendRedirect("/"); // No logged-in user found, so redirect to login page.
            return false;
        }
        response.setHeader("Set-Cookie", "JSESSIONID=" + request.getRequestedSessionId() + "; SameSite=None; Secure");
        return true;
    }
}
