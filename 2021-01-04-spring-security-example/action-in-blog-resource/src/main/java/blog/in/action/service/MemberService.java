package blog.in.action.service;

import blog.in.action.entity.Member;
import blog.in.action.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findById(String id) {
        Optional<Member> option = memberRepository.findById(id);
        if (!option.isPresent()) {
            return null;
        }
        return option.get();
    }
}