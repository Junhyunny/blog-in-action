package blog.in.action.service;

import blog.in.action.domain.Member;
import blog.in.action.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void registerMember(Member member) {
        memberRepository.save(member);
    }

    public Member findById(String id) {
        Optional<Member> option = memberRepository.findById(id);
        return option.orElse(null);
    }

    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }
}