package blog.in.action.flush;

import blog.in.action.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Log4j2
@DataJpaTest
public class CommitOptionTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    void transaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            consumer.accept(entityManager);
        } catch (Exception ex) {
            transaction.rollback();
            throw ex;
        } finally {
            entityManager.close();
        }
    }

    @Test
    void how_to_work_when_commit_option() {
        transaction(entityManager -> {
            entityManager.setFlushMode(FlushModeType.COMMIT);
            Member member = Member.builder()
                    .id("Junhyunny")
                    .name("Junhyun Kang")
                    .contacts(Arrays.stream(new String[]{"kang3966@naver.com"}).collect(Collectors.toList()))
                    .build();
            entityManager.persist(member);
            member.setName("Junhyun");
            member.appendContact("010-1234-1234");


            log.info("===== BEFORE JPQL =====");
            String jpqlQuery = "update Member m set m.contacts = '010-3214-3214' where m.name = 'Junhyun'";
            int resultCnt = entityManager.createQuery(jpqlQuery).executeUpdate();
            log.info("===== AFTER JPQL =====");


            Member result = entityManager.find(Member.class, "Junhyunny");
            List<String> contacts = result.getContacts();
            assertThat(resultCnt, equalTo(0));
            assertThat(contacts.size(), equalTo(2));
            assertThat(contacts.get(0), equalTo("kang3966@naver.com"));
            assertThat(contacts.get(1), equalTo("010-1234-1234"));
        });
    }
}
