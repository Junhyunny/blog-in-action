package blog.in.action.findby;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootTest
public class FindByTest {

    private static final String parentKey = "parentKey";
    private static final String childKey = "childKey";

    @Autowired
    private ParentEntityRepository parentEntityRepository;

    @Autowired
    private ChildEntityRepository childEntityRepository;

    @BeforeEach
    public void beforeEach() {
        parentEntityRepository.deleteAll();
        childEntityRepository.deleteAll();
        ParentEntity parentEntity = new ParentEntity(parentKey);
        parentEntity.setChildEntity(new ChildEntity(childKey, parentEntity));
        parentEntityRepository.saveAndFlush(parentEntity);
    }

    @Test
    public void test_withoutVersionNo_throwException() {
        ParentEntity parentEntity = new ParentEntity(parentKey);
        assertThrows(InvalidDataAccessApiUsageException.class, () -> childEntityRepository.findByParentEntity(parentEntity));
    }

    @Test
    public void test_withVersionNo_isPresent() {
        ParentEntity parentEntity = new ParentEntity(parentKey);
        parentEntity.setVersionNo(99L);
        Assertions.assertThat(childEntityRepository.findByParentEntity(parentEntity).isPresent()).isTrue();
    }
}

interface ParentEntityRepository extends JpaRepository<ParentEntity, String> {

}

interface ChildEntityRepository extends JpaRepository<ChildEntity, String> {

    Optional<ChildEntity> findByParentEntity(ParentEntity parentEntity);
}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_PARENT")
class ParentEntity {

    public ParentEntity(String id) {
        this.id = id;
    }

    @Id
    private String id;

    @OneToOne(mappedBy = "parentEntity", cascade = CascadeType.ALL)
    private ChildEntity childEntity;

    @Version
    private Long versionNo;
}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_CHILD")
class ChildEntity {

    public ChildEntity(String id, ParentEntity parentEntity) {
        this.id = id;
        this.parentEntity = parentEntity;
    }

    @Id
    private String id;

    @OneToOne
    private ParentEntity parentEntity;

    @Version
    private Long versionNo;
}