package action.in.blog.controller;

import action.in.blog.domain.token.AuthenticatedUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal AuthenticatedUser user) {
        var mav = new ModelAndView("home");
        mav.addObject("email", user.getEmail());
        mav.addObject("nickName", user.getNickName());
        return mav;
    }
}
