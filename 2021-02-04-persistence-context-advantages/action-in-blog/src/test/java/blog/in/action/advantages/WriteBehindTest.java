package blog.in.action.advantages;

import blog.in.action.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class WriteBehindTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @BeforeEach
    private void beforeEach() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "01012341235");
            if (member != null) {
                em.remove(member);
            }
            member = em.find(Member.class, "01012341236");
            if (member != null) {
                em.remove(member);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.error("exception occurs", ex);
        } finally {
            em.close();
        }
    }

    @Test
    public void test() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();

            // memberA 등록
            Member memberA = new Member();
            memberA.setId("01012341235");
            memberA.setPassword("1234");
            List<String> authorities = new ArrayList<>();
            authorities.add("ADMIN");
            memberA.setAuthorities(authorities);
            memberA.setMemberName("Junhyunny");
            memberA.setMemberEmail("kang3966@naver.com");

            log.info("memberA persist 수행");
            em.persist(memberA);

            // memberB 등록
            Member memberB = new Member();
            memberB.setId("01012341236");
            memberB.setPassword("1234");
            authorities = new ArrayList<>();
            authorities.add("MEMBER");
            memberB.setAuthorities(authorities);
            memberB.setMemberName("Inkyungee");
            memberB.setMemberEmail("inkyungee@naver.com");

            log.info("memberB persist 수행");
            em.persist(memberB);

            log.info("commit 수행 전");
            em.getTransaction().commit();
            log.info("commit 수행 후");

        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.error("exception occurs", ex);
        } finally {
            em.close();
        }
    }
}
