package blog.in.action.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import blog.in.action.domain.Member;
import blog.in.action.domain.MemberService;

@Controller
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    private List<MemberDto> getAllMembers() {
        List<Member> memberList = memberService.findAll();
        return memberList
                .stream()
                .map(member -> MemberDto.builder()
                        .id(member.getId())
                        .memberName(member.getMemberName())
                        .memberEmail(member.getMemberEmail())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("memberList", getAllMembers());
        return "index";
    }

    @PostMapping(path = "/index")
    @Transactional(propagation = Propagation.REQUIRED)
    public String register(HttpServletRequest servletRequest, Model model) {
        Member member = new Member();
        member.setId(servletRequest.getParameter("id"));
        member.setPassword(servletRequest.getParameter("password"));
        member.setMemberName(servletRequest.getParameter("memberName"));
        member.setMemberEmail(servletRequest.getParameter("memberEmail"));
        memberService.registMember(member);
        model.addAttribute("memberList", getAllMembers());
        return "index";
    }
}
