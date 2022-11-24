package app.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

public class SessionFilter extends OncePerRequestFilter {

    private final String redirectUrl;

    public SessionFilter(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        System.out.println(session);
        if (session != null) {
            filterChain.doFilter(new UserPrincipalHttpServletRequest(request), response);
            return;
        }
        if ("/test-2".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        response.sendRedirect("/test-2");
    }

    private class UserPrincipalHttpServletRequest extends HttpServletRequestWrapper {

        public UserPrincipalHttpServletRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Principal getUserPrincipal() {
            HttpSession session = getSession(false);
            String userJson = (String) session.getAttribute("some-token");
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(userJson, AuthUser.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
