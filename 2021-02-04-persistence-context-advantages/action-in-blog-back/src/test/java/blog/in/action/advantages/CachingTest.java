package blog.in.action.advantages;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import blog.in.action.entity.Member;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
public class CachingTest {

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
				List<String> authorities = new ArrayList<>();
				authorities.add("ADMIN");
				member.setAuthorities(authorities);
				member.setMemberName("Junhyunny");
				member.setMemberEmail("kang3966@naver.com");
				em.persist(member);
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
			Member member = em.find(Member.class, "01012341234");
			Member cachedMember = em.find(Member.class, "01012341234");
			log.info("member 주소: " + System.identityHashCode(member) + ", cachedMember 주소: " + System.identityHashCode(cachedMember));
			assertTrue(member == cachedMember);
		} catch (Exception ex) {
			em.getTransaction().rollback();
			log.error("exception occurs", ex);
		} finally {
			em.close();
		}
	}
}
