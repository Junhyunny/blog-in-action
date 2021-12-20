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

    @Override
    public void run(String... args) throws Exception {
        memberRepository.save(new Member("Junhyunny", passwordEncoder.encode("123"), Collections.singletonList("ADMIN")));
    }

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }
}
