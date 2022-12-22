package action.in.blog.annotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PcAnnotationServiceIT {

    @PersistenceUnit
    EntityManagerFactory factory;

    @Autowired
    PcAnnotationService sut;

    @Test
    @DisplayName("정상 처리되면 데이터베이스에 데이터가 저장된다.")
    void find_entity_with_exception_after_insert() {

        EntityManager entityManager = factory.createEntityManager();


        PcAnnotationEntity pcAnnotationEntity = sut.findEntityAfterInsert("Hello Word", false);


        PcAnnotationEntity result = entityManager.find(PcAnnotationEntity.class, pcAnnotationEntity.getId());
        assertThat(result.getName(), equalTo(pcAnnotationEntity.getName()));
    }

    @Test
    @DisplayName("비즈니스 로직 중간에 예외가 발생하는 경우 데이터가 롤백된다.")
    void rollback_data_with_exception_after_insert() {
        EntityManager entityManager = factory.createEntityManager();


        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sut.findEntityAfterInsert("Hello World", true);
        });
        assertThat(exception.getMessage(), equalTo("throw intentionallyException"));
        assertThrows(NoResultException.class, () -> {
            TypedQuery<PcAnnotationEntity> query = entityManager.createQuery("select pc from PcAnnotationEntity pc where pc.name = :name", PcAnnotationEntity.class);
            query.setParameter("name", "Hello World");
            query.getSingleResult();
        });
    }
}
