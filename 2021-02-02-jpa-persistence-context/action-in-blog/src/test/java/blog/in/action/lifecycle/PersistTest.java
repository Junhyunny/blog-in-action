package blog.in.action.lifecycle;

import blog.in.action.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@SpringBootTest(properties = {
        "spring.jpa.show-sql=true",
})
public class PersistTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    void persistAndClear(EntityManager em, Member member) {
        em.getTransaction().begin();
        em.persist(member);
        em.getTransaction().commit();
        em.clear();
    }

    @Test
    void find_member_after_persist() {
        EntityManager em = factory.createEntityManager();
        try {
            Member member = new Member();
            member.setId("010-1234-1234");
            member.setName("Junhyunny");
            persistAndClear(em, member);

            member = em.find(Member.class, "010-1234-1234");

            assertThat(member.getId(), equalTo("010-1234-1234"));
            assertThat(member.getName(), equalTo("Junhyunny"));
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }
}
