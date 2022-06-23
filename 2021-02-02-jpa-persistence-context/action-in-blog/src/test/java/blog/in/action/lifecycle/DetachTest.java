package blog.in.action.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import blog.in.action.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class DetachTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @BeforeEach
    void beforeEach() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "01012341234");
            if (member == null) {
                member = new Member();
                member.setId("01012341234");
                member.setPassword("1234");
                List<String> authorities = new ArrayList<>();
                authorities.add("ADMIN");
                member.setAuthorities(authorities);
                member.setMemberName("Junhyunny");
                member.setMemberEmail("kang3966@naver.com");
                em.persist(member);
            } else {
                List<String> authorities = new ArrayList<>();
                authorities.add("ADMIN");
                member.setAuthorities(authorities);
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
    @Order(value = 0)
    void detachTest() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "01012341234");
            if (member != null) {
                // 영속된 객체를 detached 상태로 변경 후 값 변경
                log.info("detach 이후 객체의 값을 변경합니다.");
                em.detach(member);
                List<String> authorities = new ArrayList<>();
                authorities.add("DETACHED_ADMIN");
                member.setAuthorities(authorities);
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
    @Order(value = 1)
    void valueCheckTest() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "01012341234");
            if (member != null) {
                String actual = member.getAuthorities().get(0);
                assertEquals("ADMIN", actual);
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
    @Order(value = 2)
    void detachRemoveTest() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Member member = em.find(Member.class, "01012341234");
            if (member != null) {
                // 영속된 객체를 detached 상태로 변경 후 remove
                log.info("detach 이후 객체를 삭제합니다.");
                em.detach(member);
                assertThrows(IllegalArgumentException.class, () -> em.remove(member));
            }
        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.error("exception occurs", ex);
        } finally {
            em.close();
        }
    }
}
