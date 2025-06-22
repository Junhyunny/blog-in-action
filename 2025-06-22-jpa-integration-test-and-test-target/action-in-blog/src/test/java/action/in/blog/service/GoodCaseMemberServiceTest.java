package action.in.blog.service;

import action.in.blog.domain.MemberEntity;
import action.in.blog.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
class GoodCaseMemberServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    MemberService sut;

    @BeforeEach
    void setUp() {
        sut = new MemberService(memberRepository);
    }

    @Test
    void givenMemberWithSameMailIsDisabled_whenCreateEnableMember_thenNewMemberIsSaved_1() {
        memberRepository.save(
                new MemberEntity("user@example.com", false)
        );


        var saved = sut.createEnableMember("user@example.com");


        var result = em.createQuery(
                        "select m from MemberEntity m where m.email = :email and m.enable = true ",
                        MemberEntity.class
                )
                .setParameter("email", "user@example.com")
                .getSingleResult();
        assertThat(
                result.getId(),
                equalTo(saved.getId())
        );
    }

    @Test
    void givenMemberWithSameMailIsDisabled_whenCreateEnableMember_thenNewMemberIsSaved_2() {
        memberRepository.save(
                new MemberEntity("user@example.com", false)
        );


        var saved = sut.createEnableMember("user@example.com");


        var result = em.find(MemberEntity.class, saved.getId());
        assertThat(
                result.getId(),
                equalTo(saved.getId())
        );
        assertThat(
                result.getEmail(),
                equalTo("user@example.com")
        );
        assertThat(
                result.isEnable(),
                equalTo(true)
        );
    }
}