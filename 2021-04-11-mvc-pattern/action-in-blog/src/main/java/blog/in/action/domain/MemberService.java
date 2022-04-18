package blog.in.action.domain;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member registMember(Member member) {
        return memberRepository.save(member);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}