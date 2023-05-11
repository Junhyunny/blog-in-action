package blog.in.action.filters;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import java.io.IOException;

@Log4j2
public class BlogFilter implements Filter {

    private final String value;

    public BlogFilter(String value) {
        this.value = value;
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("==========\t" + value + " init filter");
    }

    @Override
    public void destroy() {
        log.info("==========\t" + value + " destroy filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("==========\t" + value + " before doFilter");
        chain.doFilter(request, response);
        log.info("==========\t" + value + " after doFilter");
    }
}
