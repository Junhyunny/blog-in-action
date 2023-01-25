package action.in.blog.dsl;

import action.in.blog.dsl.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.*;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
public class UpdateQueryPostStoreIT {

    @PersistenceUnit
    EntityManagerFactory factory;

    void transaction(Consumer<EntityManager> consumer) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        try {
            consumer.accept(em);
        } catch (Exception ex) {
            throw ex;
        } finally {
            transaction.rollback();
            em.close();
        }
    }

    void flushAndClear(EntityManager em) {
        em.flush();
        em.clear();
    }

    @Test
    void optimistic_lock_with_update_query() {
        transaction((em) -> {
            PostEntity entity = PostEntity.builder()
                    .title("Hello World Title")
                    .contents("This is Contents")
                    .build();
            em.persist(entity);
            flushAndClear(em);

            long obsoleteVersionNo = entity.getVersionNo();
            UpdateQueryPostStore sut = new UpdateQueryPostStore(em);


            sut.updateEntity(
                    PostEntity.builder()
                            .id(entity.getId())
                            .title("Changed title by first transaction")
                            .contents("Changed contents by first transaction")
                            .versionNo(entity.getVersionNo())
                            .build()
            );
            assertThrows(OptimisticLockException.class, () -> {
                sut.updateEntity(
                        PostEntity.builder()
                                .id(entity.getId())
                                .title("Changed title by second transaction")
                                .contents("Changed contents by second transaction")
                                .versionNo(obsoleteVersionNo)
                                .build()
                );
            });


            PostEntity result = em.find(PostEntity.class, entity.getId());
            assertThat(result.getTitle()).isEqualTo("Changed title by first transaction");
            assertThat(result.getContents()).isEqualTo("Changed contents by first transaction");
            assertThat(result.getVersionNo()).isEqualTo(1);
        });
    }
}
