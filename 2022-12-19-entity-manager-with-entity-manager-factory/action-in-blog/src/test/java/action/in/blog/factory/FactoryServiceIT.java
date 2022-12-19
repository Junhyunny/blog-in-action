package action.in.blog.factory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class FactoryServiceIT {

    @PersistenceUnit
    EntityManagerFactory factory;
    @Autowired
    FactoryService sut;

    @Test
    @DisplayName("정상 처리되면 데이터베이스에 데이터가 저장된다.")
    void find_entity_with_exception_after_insert() {
        EntityManager entityManager = factory.createEntityManager();


        FactoryEntity factoryEntity = sut.findEntityAfterInsert("Hello Word", false);


        FactoryEntity result = entityManager.find(FactoryEntity.class, factoryEntity.getId());
        assertThat(result.getName(), equalTo(factoryEntity.getName()));
    }

    @Test
    @DisplayName("비즈니스 로직 중간에 예외가 발생하는 경우 데이터가 롤백된다.")
    void rollback_data_with_exception_after_insert() {
        EntityManager entityManager = factory.createEntityManager();


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sut.findEntityAfterInsert("Hello World", true);
        });
        assertThat(exception.getMessage(), equalTo("java.lang.RuntimeException: throw intentionallyException"));
        assertThrows(NoResultException.class, () -> {
            FactoryStore store = new FactoryStore();
            store.findByName(entityManager, "Hello World");
        });
    }
}
