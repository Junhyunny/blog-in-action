package blog.in.action.lifecycle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.boot.test.context.SpringBootTest;

import blog.in.action.entity.Member;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
public class PersistTest {

	@PersistenceUnit
	private EntityManagerFactory factory;

	@Test
	@Order(value = 0)
	void persistTest() {
		EntityManager em = factory.createEntityManager();
		log.info("entityManager properties : " + em.getProperties());
		try {
			// 트랜잭션 시작
			em.getTransaction().begin();
			// 조회
			Member member = em.find(Member.class, "01012341234");
			if (member != null) {
				// 영속된 객체 값 변경
				log.info("영속된 객체의 값을 변경합니다.");
				List<String> authorities = new ArrayList<>();
				authorities.add("MEMBER");
				member.setAuthroities(authorities);
			} else {
				// 새로운 객체 생성
				log.info("새로운 객체를 생성합니다.");
				member = new Member();
				member.setId("01012341234");
				member.setPassword("1234");
				List<String> authorities = new ArrayList<>();
				authorities.add("ADMIN");
				member.setAuthroities(authorities);
				member.setMemberName("Junhyunny");
				member.setMemberEmail("kang3966@naver.com");
				// persistence context에 등록
				em.persist(member);
			}
			// 트랜잭션 종료
			em.getTransaction().commit();
		} catch (Exception ex) {
			// 트랜잭션 롤백
			em.getTransaction().rollback();
			log.error("exception occurs", ex);
		} finally {
			em.close();
		}
	}

	@Test
	@Order(value = 1)
	void valuCheckTest() {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			Member member = em.find(Member.class, "01012341234");
			if (member != null) {
				String actual = member.getAuthroities().get(0);
				assertEquals("ADMIN", actual);
				assertEquals("MEMBER", actual);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			em.getTransaction().rollback();
			log.error("exception occurs", ex);
		} finally {
			em.close();
		}
	}
}
