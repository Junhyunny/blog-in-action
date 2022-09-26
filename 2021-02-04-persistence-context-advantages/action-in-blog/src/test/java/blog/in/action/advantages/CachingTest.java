package blog.in.action.advantages;

import blog.in.action.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Log4j2
@SpringBootTest(properties = {"spring.jpa.show-sql=true"})
public class CachingTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @BeforeEach
    private void beforeEach() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = new Member();
            member.setId("010-1234-1234");
            member.setName("Junhyunny");
            em.persist(member);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }

    @Test
    public void two_members_are_same_object() {
        EntityManager em = factory.createEntityManager();
        try {

            Member member = em.find(Member.class, "010-1234-1234");
            Member cachedMember = em.find(Member.class, "010-1234-1234");

            assertThat(member == cachedMember, equalTo(true));
            assertThat(member, equalTo(cachedMember));
            assertThat(System.identityHashCode(member), equalTo(System.identityHashCode(cachedMember)));

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }
}
