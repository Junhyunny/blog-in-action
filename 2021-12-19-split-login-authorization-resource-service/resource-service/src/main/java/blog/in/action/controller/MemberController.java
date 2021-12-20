package blog.in.action.controller;

import blog.in.action.dto.MemberDto;
import blog.in.action.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/{id}")
    public MemberDto requestUserInfo(@PathVariable("id") String id) {
        return MemberDto.of(memberService.findById(id));
    }
}