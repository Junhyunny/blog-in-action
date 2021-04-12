package blog.in.action.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import blog.in.action.domain.member.Member;
import blog.in.action.domain.member.MemberService;

@Controller
@RequestMapping(value = "/jsp/member")
public class JspController {

	private final MemberService memberService;

	public JspController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/index")
	public String index(Model model) {
		List<Member> memberList = memberService.findAll();
		model.addAttribute("memberList", memberList);
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
		List<Member> memberList = memberService.findAll();
		model.addAttribute("memberList", memberList);
		return "index";
	}
}
