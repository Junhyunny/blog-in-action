package action.in.blog.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.Subject;
import java.io.IOException;
import java.security.Principal;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        filterChain.doFilter(new WrapperServletRequest(request), response);
    }
}

class WrapperServletRequest extends HttpServletRequestWrapper {

    public WrapperServletRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public Principal getUserPrincipal() {
        return new CustomPrincipal();
    }
}

class CustomPrincipal implements Principal {

    @Override
    public String getName() {
        return "junhyunny";
    }

    @Override
    public boolean implies(Subject subject) {
        return Principal.super.implies(subject);
    }
}
