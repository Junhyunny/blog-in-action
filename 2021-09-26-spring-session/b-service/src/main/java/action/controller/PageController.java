package action.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {

    @Value("${spring.application.name}")
    private String applicationName;

    private final String KEY = "controllerCount";

    @RequestMapping
    public ModelAndView index(ServletRequest request) {
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        if (session != null) {
            Integer count = (Integer) session.getAttribute(KEY);
            if (count == null) {
                count = -1;
            }
            session.setAttribute(KEY, count + 1);
        }
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("applicationName", applicationName);
        mav.addObject("session", session);
        return mav;
    }
}
