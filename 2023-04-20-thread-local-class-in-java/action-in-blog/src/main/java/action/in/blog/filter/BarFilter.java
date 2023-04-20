package action.in.blog.filter;

import action.in.blog.domain.AuthenticatedUser;
import action.in.blog.util.AuthenticatedUserHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class BarFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            AuthenticatedUser authenticatedUser = AuthenticatedUser.builder()
                    .id("0001")
                    .name(Thread.currentThread().getName())
                    .roles(Arrays.asList("ADMIN", "USER", "MANAGER"))
                    .build();
            AuthenticatedUserHolder.setUser(authenticatedUser);
            log.info("{} in bar filter", authenticatedUser);
            filterChain.doFilter(request, response);
        } finally {
            AuthenticatedUserHolder.remove();
        }
    }
}
