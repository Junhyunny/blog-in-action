package action.in.blog.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilter extends OncePerRequestFilter {

    private final String sessionCreationUri = "/session/creation";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (sessionCreationUri.equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        HttpSession httpSession = request.getSession(false);
        if (httpSession == null) {
            response.sendRedirect(sessionCreationUri);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
