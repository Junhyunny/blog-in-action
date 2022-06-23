package blog.in.action;

import blog.in.action.domain.member.Member;
import blog.in.action.domain.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActionInBlogApplication implements CommandLineRunner {

    @Autowired
    private MemberService memberService;

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        memberService.registerMember(Member.builder()
                .id("Junhyunny")
                .memberName("Junhyunny")
                .password("123")
                .build());
    }
}
