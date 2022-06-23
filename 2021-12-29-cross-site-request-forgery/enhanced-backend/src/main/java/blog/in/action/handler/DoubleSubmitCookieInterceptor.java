package blog.in.action.handler;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DoubleSubmitCookieInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 헤더로 전달된 csrf 토큰 값
        String paramToken = request.getHeader("X-CSRF-HEADER");
        String cookieToken = null;
        for (Cookie cookie : request.getCookies()) {
            if ("CSRF_TOKEN".equals(cookie.getName())) { // 쿠키로 전달되 csrf 토큰 값
                cookieToken = cookie.getValue();
                // 재사용이 불가능하도록 쿠키 만료
                cookie.setPath("/");
                cookie.setValue("");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
        // 두 값이 일치하는 지 검증
        if (cookieToken == null || !cookieToken.equals(paramToken)) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
