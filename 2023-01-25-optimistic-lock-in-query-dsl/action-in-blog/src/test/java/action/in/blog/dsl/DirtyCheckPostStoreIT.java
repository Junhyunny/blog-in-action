package action.in.blog.dsl;

import action.in.blog.dsl.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DirtyCheckPostStoreIT {

    @PersistenceUnit
    EntityManagerFactory factory;

    void transactionCommit(Consumer<EntityManager> consumer) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            consumer.accept(em);
        } catch (Exception ex) {
            throw ex;
        } finally {
            transaction.commit();
            em.close();
        }
    }

    CompletableFuture<Void> transactionAsyncWithCommit(Consumer<EntityManager> consumer) {
        return CompletableFuture.runAsync(() -> {
            EntityManager em = factory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {
                consumer.accept(em);
            } catch (Exception ex) {
                throw ex;
            } finally {
                transaction.commit();
                em.close();
            }
        });
    }

    @Test
    void optimistic_lock_with_select_query_and_lock_type() {
        PostEntity entity = PostEntity.builder()
                .title("Hello World Title")
                .contents("This is Contents")
                .build();
        transactionCommit(em -> {
            em.persist(entity);
        });


        CompletableFuture<Void> tx1 = transactionAsyncWithCommit((em) -> {
            DirtyCheckPostStore sut = new DirtyCheckPostStore(em);
            sut.updateEntityWithLongTransaction(
                    PostEntity.builder()
                            .id(entity.getId())
                            .title("Changed title with long transaction")
                            .contents("Changed contents with long transaction")
                            .build()
            );
        }).exceptionally(exception -> {
            Throwable throwable = exception.getCause();
            assertThat(throwable.getCause()).isInstanceOf(OptimisticLockException.class);
            return null;
        });
        CompletableFuture<Void> tx2 = transactionAsyncWithCommit((em) -> {
            DirtyCheckPostStore sut = new DirtyCheckPostStore(em);
            sut.updateEntity(
                    PostEntity.builder()
                            .id(entity.getId())
                            .title("Changed title with short transaction")
                            .contents("Changed contents with short transaction")
                            .build()
            );
        });
        tx1.join();
        tx2.join();


        transactionCommit(em -> {
            PostEntity result = em.find(PostEntity.class, entity.getId());
            assertThat(result.getTitle()).isEqualTo("Changed title with short transaction");
            assertThat(result.getContents()).isEqualTo("Changed contents with short transaction");
            assertThat(result.getVersionNo()).isEqualTo(1);
        });
    }
}
