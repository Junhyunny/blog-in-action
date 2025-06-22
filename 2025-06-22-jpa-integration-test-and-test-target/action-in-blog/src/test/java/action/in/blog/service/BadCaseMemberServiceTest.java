package action.in.blog.service;

import action.in.blog.domain.MemberEntity;
import action.in.blog.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class BadCaseMemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    MemberService sut;

    @BeforeEach
    void setUp() {
        sut = new MemberService(memberRepository);
    }

    @Test
    void givenMemberWithSameMailIsDisabled_whenCreateEnableMember_thenNewMemberIsSaved() {
        // given - save disabled user with a same email
        memberRepository.save(
                new MemberEntity("user@example.com", false)
        );


        // when - save enable member
        var saved = sut.createEnableMember("user@example.com");


        // then - find enable member
        var result = memberRepository.findEnableMemberByEmail(
                "user@example.com"
        ).orElseThrow();
        assertThat(
                result.getId(),
                equalTo(saved.getId())
        );
    }
}