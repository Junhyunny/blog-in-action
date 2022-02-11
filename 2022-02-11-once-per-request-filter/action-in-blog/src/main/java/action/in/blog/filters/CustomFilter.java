package action.in.blog.filters;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import java.io.IOException;

@Log4j2
public class CustomFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilter in CustomFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
