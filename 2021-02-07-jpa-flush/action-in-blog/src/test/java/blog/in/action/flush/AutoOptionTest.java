package blog.in.action.flush;

import static org.junit.jupiter.api.Assertions.assertTrue;
import blog.in.action.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class AutoOptionTest {

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
        em.setFlushMode(FlushModeType.AUTO);
        try {
            em.getTransaction().begin();

            Member member = em.find(Member.class, "01012341234");
            List<String> authorities = new ArrayList<>(member.getAuthorities());
            authorities.add("MEMBER");
            member.setAuthorities(authorities);

            log.info("JPQL 쿼리 수행 전입니다.");

            String jpql = "update Member m set m.authorities = 'JQPL_MEMBER' where m.authorities like '%MEMBER%'";
            em.createQuery(jpql).executeUpdate();

            jpql = "select m from Member m where m.authorities like '%JQPL_MEMBER%'";

            TypedQuery<Member> query = em.createQuery(jpql, Member.class);
            List<Member> jpqlMember = query.getResultList();

            log.info("JPQL 쿼리 수행 후입니다.");

            assertTrue(jpqlMember.size() > 0);

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.error("exception occurs", ex);
        } finally {
            em.close();
        }
    }
}
