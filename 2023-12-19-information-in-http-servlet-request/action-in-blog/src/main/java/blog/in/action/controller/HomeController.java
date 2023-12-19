package blog.in.action.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home(HttpServletRequest httpServletRequest) {
        return String.format("""
                            method - %s<br/>
                            scheme - %s<br/>
                            serverName - %s<br/>
                            serverPort - %s<br/>
                            contextPath - %s<br/>
                            servletPath - %s<br/>
                            requestURI - %s<br/>
                            requestURL - %s<br/>
                            queryString - %s
                        """,
                httpServletRequest.getMethod(),
                httpServletRequest.getScheme(),
                httpServletRequest.getServerName(),
                httpServletRequest.getServerPort(),
                httpServletRequest.getContextPath(),
                httpServletRequest.getServletPath(),
                httpServletRequest.getRequestURI(),
                httpServletRequest.getRequestURL(),
                httpServletRequest.getQueryString()
        );
    }
}
