package blog.in.action.findby;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootTest
public class FindByTest {

    private static final String childEntityId = "childEntityId";

    @Autowired
    private ParentEntityRepository parentEntityRepository;

    @Autowired
    private ChildEntityRepository childEntityRepository;

    @BeforeEach
    public void beforeEach() {
        parentEntityRepository.deleteAll();
        childEntityRepository.deleteAll();
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setValue("findValue");
        parentEntity.setChildEntity(new ChildEntity("childEntityId", parentEntity));
        parentEntityRepository.save(parentEntity);
    }

    @Test
    public void test_withoutParentNoChildEntity_throwException() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setId(childEntityId);
        assertThrows(Exception.class, () -> parentEntityRepository.findByChildEntity(childEntity));
    }

    @Test
    public void test_withParentNoChildEntity_isNotEqualWithReturned() {
        ChildEntity childEntity = new ChildEntity();
        childEntity.setId(childEntityId);
        childEntity.setVersionNo(0L);
        Assertions.assertThat(parentEntityRepository.findByChildEntity(childEntity)).isNotEmpty();
    }
}

interface ParentEntityRepository extends JpaRepository<ParentEntity, Long> {

    List<ParentEntity> findByChildEntity(ChildEntity childEntity);
}

interface ChildEntityRepository extends JpaRepository<ChildEntity, String> {

}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_PARENT")
class ParentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "VALUE")
    private String value;

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