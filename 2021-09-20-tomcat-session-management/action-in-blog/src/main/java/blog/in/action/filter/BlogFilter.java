package blog.in.action.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class BlogFilter implements Filter {

    private final String KEY = "filterCount";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            Integer count = (Integer) session.getAttribute(KEY);
            if (count == null) {
                count = -1;
            }
            session.setAttribute(KEY, count + 1);
        }
        chain.doFilter(request, response);
    }
}