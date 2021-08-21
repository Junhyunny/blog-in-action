package blog.in.action.clear;

import static org.junit.jupiter.api.Assertions.assertTrue;
import blog.in.action.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class WrongScenarioTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @BeforeEach
    private void beforeEach() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "01012341234");
            if (member == null) {
                member = new Member();
                member.setId("01012341234");
                member.setPassword("1234");
                member.setMemberName("Junhyunny");
                member.setMemberEmail("kang3966@naver.com");
                em.persist(member);
            } else {
                member.setAuthorities(new ArrayList<>());
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

            Member member = em.find(Member.class, "01012341234");

            List<String> authorities = new ArrayList<>();
            authorities.add("MEMBER");
            member.setAuthorities(authorities);

            String jpql = "update Member m set m.authorities = 'JQPL_MEMBER' where m.id = '01012341234'";
            em.createQuery(jpql).executeUpdate();

            jpql = "select m from Member m where m.id = '01012341234'";
            TypedQuery<Member> query = em.createQuery(jpql, Member.class);
            Member jpqlMember = query.getSingleResult();

            assertTrue(System.identityHashCode(member) == System.identityHashCode(jpqlMember));
            log.info("member 객체의 권한: " + member.getAuthorities());
            log.info("jpqlMember 객체의 권한: " + jpqlMember.getAuthorities());

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.error("exception occurs", ex);
        } finally {
            em.close();
        }
    }
}