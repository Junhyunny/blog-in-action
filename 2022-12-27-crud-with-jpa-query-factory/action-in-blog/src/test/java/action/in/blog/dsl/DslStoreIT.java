package action.in.blog.dsl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class DslStoreIT {

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
    void createEntity() {
        transaction((em) -> {
            DslEntity dslEntity = new DslEntity("Hello World");


            DslStore sut = new DslStore(em);
            sut.createEntity(dslEntity);


            flushAndClear(em);
            DslEntity result = em.find(DslEntity.class, dslEntity.getId());
            assertThat(result.getSomeValue(), equalTo("Hello World"));
        });
    }

    @Test
    void getEntityByContains() {
        transaction((em) -> {
            DslEntity dslEntity = new DslEntity("Hello World");
            em.persist(dslEntity);
            flushAndClear(em);


            DslStore sut = new DslStore(em);
            List<DslEntity> result = sut.getEntityByContains("llo Wor");


            DslEntity firstItem = result.get(0);
            assertThat(result.size(), equalTo(1));
            assertThat(firstItem.getSomeValue(), equalTo("Hello World"));
        });
    }

    @Test
    void updateEntity() {
        transaction((em) -> {
            DslEntity dslEntity = new DslEntity("Hello World");
            em.persist(dslEntity);
            flushAndClear(em);


            DslStore sut = new DslStore(em);
            sut.updateEntity(new DslEntity(dslEntity.getId(), "Hello QueryDSL World"));


            flushAndClear(em);
            DslEntity result = em.find(DslEntity.class, dslEntity.getId());
            assertThat(result.getSomeValue(), equalTo("Hello QueryDSL World"));
        });
    }

    @Test
    void deleteEntity() {
        transaction((em) -> {
            DslEntity dslEntity = new DslEntity("Hello World");
            em.persist(dslEntity);
            flushAndClear(em);


            DslStore sut = new DslStore(em);
            sut.deleteEntity(dslEntity.getId());


            flushAndClear(em);
            DslEntity result = em.find(DslEntity.class, dslEntity.getId());
            assertThat(result == null, equalTo(true));
        });
    }
}
