package blog.in.action.controller;

import blog.in.action.dto.AuthenticatedUser;
import blog.in.action.entity.Member;
import blog.in.action.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/user-info")
    public Member requestUserInfo(@RequestParam("id") String id) {
        return memberService.findById(id);
    }

    @GetMapping("/search/joined-date")
    public List<Member> searchUsersByJoinedDate(LocalDate beginDate, LocalDate endDate) {
        return Arrays.asList(
                Member.builder()
                        .name("Junhyunny")
                        .joinedDate(beginDate)
                        .build(),
                Member.builder()
                        .name("Jua")
                        .joinedDate(endDate)
                        .build()
        );
    }

    @PutMapping("/deactivate")
    public Member deactivateUser(AuthenticatedUser authenticatedUser) {
        return Member.builder()
                .id(authenticatedUser.getId())
                .activate(false)
                .build();
    }
}