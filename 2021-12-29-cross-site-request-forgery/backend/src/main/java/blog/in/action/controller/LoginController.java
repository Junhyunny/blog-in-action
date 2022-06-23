package blog.in.action.controller;

import blog.in.action.domain.member.Member;
import blog.in.action.domain.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(HttpServletRequest servletRequest) {
        String memberPassword = servletRequest.getParameter("password");
        HttpSession session = servletRequest.getSession(false);
        Member member = memberService.findById(servletRequest.getParameter("id"));
        if (member == null || memberPassword == null || !memberPassword.equals(member.getPassword())) {
            session.setAttribute("member", null);
            return "redirect:/";
        }
        session.setAttribute("member", member);
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(HttpServletRequest servletRequest, Model model) {
        HttpSession session = servletRequest.getSession(false);
        Member member = (Member) session.getAttribute("member");
        Member persistedMember = memberService.findById(member.getId());
        model.addAttribute("memberName", persistedMember.getMemberName());
        return "main";
    }
}
