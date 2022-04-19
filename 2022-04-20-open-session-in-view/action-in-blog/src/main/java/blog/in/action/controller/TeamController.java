package blog.in.action.controller;

import blog.in.action.domain.Member;
import blog.in.action.domain.MemberService;
import blog.in.action.domain.Team;
import blog.in.action.domain.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final MemberService memberService;

    @GetMapping
    public String team(Model model) {
        model.addAttribute("teamList", teamService.findAll());
        return "Team";
    }

    @PostMapping
    public String registerTeam(Model model, @ModelAttribute TeamDto teamDto) {
        Team team = new Team();
        team.setTeamName(teamDto.getTeamName());
        teamService.registerTeam(team);
        model.addAttribute("teamList", teamService.findAll());
        return "Team";
    }

    @GetMapping("/detail/{id}")
    public String teamDetail(Model model, @PathVariable long id) {
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        return "TeamDetail";
    }

    @PostMapping("/detail/{id}")
    public String registerTeamMembers(Model model, @ModelAttribute MemberDto memberDto, @PathVariable long id) {
        Member member = memberDto.toEntity();
        member.setTeam(new Team(id));
        memberService.registerMember(member);
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        return "TeamDetail";
    }
}
