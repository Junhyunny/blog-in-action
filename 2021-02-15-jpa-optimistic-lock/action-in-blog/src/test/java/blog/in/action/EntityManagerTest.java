package blog.in.action;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.*;
import java.util.concurrent.CompletableFuture;
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

    @BeforeEach
    public void beforeEach() {
        EntityManager entityManager = factory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("update Post p set p.versionNo = 0 where p.id = 1").executeUpdate();
        transaction.commit();
    }

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
    public void optimistic_lock_with_entity_manager() {
        CompletableFuture<Void> tx = transactionAsyncWithCommit(entityManager -> {
            TypedQuery<Post> typedQuery = entityManager.createQuery(selectQuery, Post.class);
            typedQuery.setLockMode(LockModeType.OPTIMISTIC);
            Post post = typedQuery.getSingleResult();
            post.setContents("This is optimistic tx1.");
            sleep(500);
        });
        Throwable throwable = assertThrows(Exception.class, () -> {
            transactionAsyncWithCommit(entityManager -> {
                TypedQuery<Post> typedQuery = entityManager.createQuery(selectQuery, Post.class);
                typedQuery.setLockMode(LockModeType.OPTIMISTIC);
                Post post = typedQuery.getSingleResult();
                post.setContents("This is optimistic tx2.");
                sleep(1000);
            }).join();
        });
        tx.join();


        Throwable cause = throwable.getCause();
        EntityManager entityManager = factory.createEntityManager();
        Post result = entityManager.createQuery(selectQuery, Post.class).getSingleResult();
        assertThat(result.getContents(), equalTo("This is optimistic tx1."));
        assertThat(result.getVersionNo(), equalTo(1L));
        assertThat(cause, instanceOf(RollbackException.class));
        assertThat(cause.getCause(), instanceOf(OptimisticLockException.class));
    }

    @Test
    public void optimistic_force_increment_lock_with_entity_manager() {
        CompletableFuture<Void> tx = transactionAsyncWithCommit(entityManager -> {
            TypedQuery<Post> typedQuery = entityManager.createQuery(selectQuery, Post.class);
            typedQuery.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            Post post = typedQuery.getSingleResult();
            post.setContents("This is optimistic force increment tx1.");
            sleep(500);
        });
        Throwable throwable = assertThrows(Exception.class, () -> {
            transactionAsyncWithCommit(entityManager -> {
                TypedQuery<Post> typedQuery = entityManager.createQuery(selectQuery, Post.class);
                typedQuery.setLockMode(LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                Post post = typedQuery.getSingleResult();
                post.setContents("This is optimistic force increment tx2.");
                sleep(1000);
            }).join();
        });
        tx.join();


        Throwable cause = throwable.getCause();
        EntityManager entityManager = factory.createEntityManager();
        Post result = entityManager.createQuery(selectQuery, Post.class).getSingleResult();
        assertThat(result.getContents(), equalTo("This is optimistic force increment tx1."));
        assertThat(result.getVersionNo(), equalTo(2L));
        assertThat(cause, instanceOf(RollbackException.class));
        assertThat(cause.getCause(), instanceOf(OptimisticLockException.class));
    }
}
