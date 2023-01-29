package blog.in.action;

import lombok.extern.log4j.Log4j2;
import org.h2.jdbc.JdbcSQLTimeoutException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
@DataJpaTest
@TestPropertySource(
        properties = {
                "spring.sql.init.mode=embedded",
                "spring.sql.init.schema-locations=classpath:db/schema.sql",
                "spring.sql.init.data-locations=classpath:db/data.sql",
                "spring.jpa.defer-datasource-initialization=true"
        }
)
public class EntityManagerTest {

    String selectQuery = "select p from Post p where p.title= 'Hello World'";

    @PersistenceUnit
    EntityManagerFactory factory;

    CompletableFuture<Void> transactionAsyncWithCommit(Consumer<EntityManager> consumer) {
        return CompletableFuture.runAsync(() -> {
            EntityManager entityManager = factory.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            try {
                consumer.accept(entityManager);
            } catch (Exception ex) {
                throw ex;
            } finally {
                transaction.commit();
                entityManager.close();
            }
        });
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void pessimistic_lock_with_entity_manager() {
        CompletableFuture<Void> tx = transactionAsyncWithCommit(entityManager -> {
            TypedQuery<Post> typedQuery = entityManager.createQuery(selectQuery, Post.class);
            typedQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            Post post = typedQuery.getSingleResult();
            post.setContents("This is pessimistic tx1.");
            log.info("This is tx1 before sleep");
            sleep(7000);
            log.info("This is tx1 after sleep");
        });
        sleep(500);
        Throwable throwable = assertThrows(Exception.class, () -> {
            transactionAsyncWithCommit(entityManager -> {
                TypedQuery<Post> typedQuery = entityManager.createQuery(selectQuery, Post.class);
                typedQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
                Post post = typedQuery.getSingleResult();
                post.setContents("This is pessimistic tx2.");
                log.info("This is tx2");
            }).join();
        });
        tx.join();


        Throwable pessimisticLock = throwable.getCause();
        Throwable hibernatePessimisticLock = pessimisticLock.getCause();
        Throwable jdbcSQLTimeout = hibernatePessimisticLock.getCause();
        EntityManager entityManager = factory.createEntityManager();
        Post result = entityManager.createQuery(selectQuery, Post.class).getSingleResult();

        assertThat(throwable, instanceOf(CompletionException.class));
        assertThat(pessimisticLock, instanceOf(PessimisticLockException.class));
        assertThat(hibernatePessimisticLock, instanceOf(org.hibernate.PessimisticLockException.class));
        assertThat(jdbcSQLTimeout, instanceOf(JdbcSQLTimeoutException.class));
        assertThat(result.getContents(), equalTo("This is pessimistic tx1."));
    }
}
