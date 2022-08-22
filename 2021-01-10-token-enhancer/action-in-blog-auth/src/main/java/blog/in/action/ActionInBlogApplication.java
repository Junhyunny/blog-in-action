package blog.in.action;

import blog.in.action.entity.Member;
import blog.in.action.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@RequiredArgsConstructor
@SpringBootApplication
public class ActionInBlogApplication implements CommandLineRunner {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        memberRepository.save(Member.builder()
                .id("Junhyunny")
                .password(passwordEncoder.encode("123"))
                .authorities(Collections.singletonList("ADMIN"))
                .build()
        );
    }
}
