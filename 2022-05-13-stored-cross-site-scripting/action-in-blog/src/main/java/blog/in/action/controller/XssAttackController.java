package blog.in.action.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class XssAttackController {

    @GetMapping(path = {"", "/"})
    public String index() {
        return "redirect:/reflected";
    }

    @GetMapping(path = "/reflected")
    public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
        model.addAttribute("keyword", keyword);
        return "ReflectedXssAttack";
    }
}
