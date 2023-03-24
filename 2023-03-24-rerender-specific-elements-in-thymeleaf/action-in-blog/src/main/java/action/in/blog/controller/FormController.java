package action.in.blog.controller;

import action.in.blog.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/user")
    public String user(@ModelAttribute User user, Model model) {
        model.addAttribute("name", user.getName());
        model.addAttribute("contact", user.getContact());
        model.addAttribute("email", user.getEmail());
        return "index::#user";
    }
}
