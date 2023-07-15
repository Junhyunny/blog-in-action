package action.in.blog.filter;

import action.in.blog.provider.AuthProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final List<String> ignorePaths;
    private final AuthProvider authProvider;

    public AuthFilter(
            @Value("${auth.ignore-paths}") List<String> ignorePaths,
            AuthProvider authProvider
    ) {
        this.ignorePaths = ignorePaths;
        this.authProvider = authProvider;
    }

    private boolean isIgnorePath(String path) {
        return ignorePaths.stream().anyMatch(ignorePaths -> ignorePaths.startsWith(path));
    }

    private void sendErrorResponse(HttpServletResponse response, Exception exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(
                exception.getMessage()
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var requestURI = request.getRequestURI();
        if (isIgnorePath(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            authProvider.authenticate();
        } catch (Exception exception) {
            sendErrorResponse(response, exception);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
