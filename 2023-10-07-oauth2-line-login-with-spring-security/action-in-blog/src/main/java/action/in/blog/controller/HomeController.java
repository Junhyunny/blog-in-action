package action.in.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal principal) {
        var mav = new ModelAndView("home");
        mav.addObject("displayName", principal.getAttribute("displayName"));
        mav.addObject("pictureUrl", principal.getAttribute("pictureUrl"));
        return mav;
    }
}
