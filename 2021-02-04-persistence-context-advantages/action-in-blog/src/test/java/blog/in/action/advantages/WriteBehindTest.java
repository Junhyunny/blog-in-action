package blog.in.action.advantages;

import blog.in.action.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

@Log4j2
@SpringBootTest(properties = {"spring.jpa.show-sql=true"})
public class WriteBehindTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @Test
    public void test() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();

            Member junhyunny = new Member();
            junhyunny.setId("010-1234-1234");
            junhyunny.setName("Junhyunny");

            Member jua = new Member();
            jua.setId("010-1235-1235");
            jua.setName("Jua");

            em.persist(junhyunny);
            em.persist(jua);

            log.info("before commit");
            em.getTransaction().commit();
            log.info("after commit");

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new RuntimeException(ex);
        } finally {
            em.close();
        }
    }
}
