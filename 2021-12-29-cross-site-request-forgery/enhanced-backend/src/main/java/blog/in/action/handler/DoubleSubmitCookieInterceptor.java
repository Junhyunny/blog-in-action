package blog.in.action.handler;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoubleSubmitCookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String paramToken = request.getHeader("X-CSRF-HEADER");  // 1
        String cookieToken = null;
        for (Cookie cookie : request.getCookies()) {
            if ("CSRF_TOKEN".equals(cookie.getName())) { // 2
                cookieToken = cookie.getValue(); // 3
                cookie.setPath("/");
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
        if (cookieToken == null || !cookieToken.equals(paramToken)) { // 4
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
