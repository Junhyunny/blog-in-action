package action.in.blog.filter;

import action.in.blog.domain.AuthenticatedUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

@Slf4j
public class FooFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                .id("0001")
                .name(Thread.currentThread().getName())
                .roles(Arrays.asList("ADMIN", "USER", "MANAGER"))
                .build();
        log.info("{} in foo filter", authenticatedUser);
        filterChain.doFilter(new UserPrincipalHttpServletRequest(request, authenticatedUser), response);
    }
}

class UserPrincipalHttpServletRequest extends HttpServletRequestWrapper {

    private Principal principal;

    public UserPrincipalHttpServletRequest(HttpServletRequest request) {
        super(request);
    }

    public UserPrincipalHttpServletRequest(HttpServletRequest request, Principal principal) {
        this(request);
        this.principal = principal;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }
}