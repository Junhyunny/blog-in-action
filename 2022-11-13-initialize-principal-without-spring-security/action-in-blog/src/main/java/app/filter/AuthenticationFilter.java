package app.filter;

import app.auth.AuthenticatedUser;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(new UserPrincipalHttpServletRequest(request), response);
    }

    private class UserPrincipalHttpServletRequest extends HttpServletRequestWrapper {

        public UserPrincipalHttpServletRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public Principal getUserPrincipal() {
            return AuthenticatedUser.builder()
                    .id("0001")
                    .name("Junhyunny")
                    .roles(Arrays.asList("ADMIN", "USER", "MANAGER"))
                    .build();
        }
    }
}