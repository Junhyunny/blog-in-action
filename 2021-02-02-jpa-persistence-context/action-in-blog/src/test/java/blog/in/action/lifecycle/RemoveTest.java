package blog.in.action.lifecycle;

import blog.in.action.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
public class RemoveTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @BeforeEach
    void beforeEach() {
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

    void removeByIdAndClear(EntityManager em, String id) {
        em.getTransaction().begin();
        Member member = em.find(Member.class, id);
        em.remove(member);
        em.getTransaction().commit();
        em.clear();
    }

    @Test
    void entity_is_null_when_find_removed_entity() {
        EntityManager em = factory.createEntityManager();
        try {
            removeByIdAndClear(em, "010-1234-1234");

            Member member = em.find(Member.class, "010-1234-1234");

            assertThat(member, equalTo(null));
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }
}
