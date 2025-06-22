package action.in.blog.service;

import action.in.blog.domain.MemberEntity;
import action.in.blog.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberEntity createEnableMember(String email) {
        if (memberRepository.findEnableMemberByEmail(email).isPresent()) {
            throw new IllegalStateException(
                    String.format("enable user with this email is exsited.(%s)", email)
            );
        }
        return memberRepository.save(
                new MemberEntity(
                        email,
                        null
                )
        );
    }
}
