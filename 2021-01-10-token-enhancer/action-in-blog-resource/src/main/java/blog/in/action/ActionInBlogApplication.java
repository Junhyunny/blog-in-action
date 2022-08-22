package blog.in.action;

import blog.in.action.entity.Member;
import blog.in.action.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class ActionInBlogApplication implements CommandLineRunner {

    private final MemberRepository memberRepository;

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        memberRepository.save(Member.builder()
                .id("Junhyunny")
                .name("Junhyunny")
                .email("junhyunny@naver.com")
                .address("Seoul")
                .build()
        );
    }
}
