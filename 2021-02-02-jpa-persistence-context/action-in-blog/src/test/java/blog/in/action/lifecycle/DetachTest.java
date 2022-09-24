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
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest(properties = {
        "spring.jpa.show-sql=true",
})
public class DetachTest {

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

    @Test
    void detached_entity_is_not_updated() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "010-1234-1234");
            em.detach(member);
            member.setName("Jua");
            em.getTransaction().commit();
            em.clear();


            member = em.find(Member.class, "010-1234-1234");
            assertThat(member.getName(), equalTo("Junhyunny"));
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }

    @Test
    void throw_exception_when_remove_detached_entity() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "010-1234-1234");
            em.detach(member);

            Throwable throwable = assertThrows(IllegalArgumentException.class, () -> em.remove(member));
            log.warn(throwable.getMessage());
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }
}
