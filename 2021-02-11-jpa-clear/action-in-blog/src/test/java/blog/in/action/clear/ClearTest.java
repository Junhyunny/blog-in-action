package blog.in.action.clear;

import blog.in.action.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Log4j2
@DataJpaTest
public class ClearTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    void transaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            consumer.accept(entityManager);
        } catch (Exception ex) {
            throw ex;
        } finally {
            transaction.rollback();
            entityManager.close();
        }
    }

    @Test
    public void without_clear() {
        transaction(entityManager -> {
            Member member = Member.builder()
                    .id("Junhyunny")
                    .name("Junhyun Kang")
                    .contacts(Arrays.stream(new String[]{"kang3966@naver.com"}).collect(Collectors.toList()))
                    .build();
            entityManager.persist(member);
            member.appendContact("010-1234-1234");


            String updateQuery = "update Member m set m.contacts = '010-4321-4321' where m.id = 'Junhyunny'";
            int result = entityManager.createQuery(updateQuery).executeUpdate();


            String selectQuery = "select m from Member m where m.name = 'Junhyun Kang'";
            Member resultMember = entityManager.createQuery(selectQuery, Member.class).getSingleResult();
            List<String> contacts = resultMember.getContacts();
            assertThat(result, equalTo(1));
            assertThat(contacts.size(), equalTo(2));
            assertThat("kang3966@naver.com", equalTo(contacts.get(0)));
            assertThat("010-1234-1234", equalTo(contacts.get(1)));
        });
    }

    @Test
    public void with_clear() {
        transaction(entityManager -> {
            Member member = Member.builder()
                    .id("Junhyunny")
                    .name("Junhyun Kang")
                    .contacts(Arrays.stream(new String[]{"kang3966@naver.com"}).collect(Collectors.toList()))
                    .build();
            entityManager.persist(member);
            member.appendContact("010-1234-1234");
            String updateQuery = "update Member m set m.contacts = '010-4321-4321' where m.id = 'Junhyunny'";
            int result = entityManager.createQuery(updateQuery).executeUpdate();


            entityManager.clear();


            String selectQuery = "select m from Member m where m.name = 'Junhyun Kang'";
            Member resultMember = entityManager.createQuery(selectQuery, Member.class).getSingleResult();
            List<String> contacts = resultMember.getContacts();
            assertThat(result, equalTo(1));
            assertThat(contacts.size(), equalTo(1));
            assertThat("010-4321-4321", equalTo(contacts.get(0)));
        });
    }
}