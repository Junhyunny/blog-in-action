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
public class DirtyCheckingTest {

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
    public void member_name_is_changed_because_of_dirty_check() {
        EntityManager em = factory.createEntityManager();
        try {

            em.getTransaction().begin();
            Member member = em.find(Member.class, "010-1234-1234");
            member.setName("Jua");
            em.getTransaction().commit();
            em.clear();

            Member target = em.find(Member.class, "010-1234-1234");
            assertThat(target.getName(), equalTo("Jua"));

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }
}
