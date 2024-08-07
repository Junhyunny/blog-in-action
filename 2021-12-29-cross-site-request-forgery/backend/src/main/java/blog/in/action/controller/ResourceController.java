package blog.in.action.controller;

import blog.in.action.domain.Member;
import blog.in.action.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ResourceController {

    private final MemberService memberService;

    public ResourceController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/change")
    public void changeMemberNameByGet(HttpServletRequest request, @RequestParam String name) {
        HttpSession httpSession = request.getSession(false);
        Member member = (Member) httpSession.getAttribute("member");
        Member persistedMember = memberService.findById(member.getId());
        persistedMember.setMemberName(name);
        memberService.updateMember(persistedMember);
    }

    @PostMapping("/change")
    public void changeMemberNameByPost(HttpServletRequest request, @ModelAttribute Member body) {
        HttpSession httpSession = request.getSession(false);
        Member member = (Member) httpSession.getAttribute("member");
        Member persistedMember = memberService.findById(member.getId());
        persistedMember.setMemberName(body.getMemberName());
        memberService.updateMember(persistedMember);
    }
}
