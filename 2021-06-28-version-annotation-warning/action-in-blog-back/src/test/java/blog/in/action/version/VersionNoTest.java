package blog.in.action.version;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootTest
public class VersionNoTest {

    @Autowired
    private DefaultVersionEntityRepository defaultVersionEntityRepository;

    @Autowired
    private NonDefaultVersionEntityRepository nonDefaultVersionEntityRepository;

    @Autowired
    private ChildEntityRepository childEntityRepository;

    @BeforeEach
    public void beforeEach() {
        defaultVersionEntityRepository.deleteAll();
        nonDefaultVersionEntityRepository.deleteAll();
        childEntityRepository.deleteAll();
    }

    @Test
    public void test_nonTransientEntity_throwException() {

        // 신규 엔티티 생성, new
        DefaultVersionEntity versionEntity = new DefaultVersionEntity();
        versionEntity.setValue("DefaultVersionEntity");
        versionEntity.setChildEntity(new ChildEntity(versionEntity));

        // 엔티티 save, persist
        defaultVersionEntityRepository.save(versionEntity);

        // 자식 엔티티, persist
        childEntityRepository.save(versionEntity.getChildEntity());
    }

    @Test
    public void test_nonThrowException() {
        NonDefaultVersionEntity nonVersionEntity = new NonDefaultVersionEntity();
        nonVersionEntity.setValue("NonDefaultVersionEntity");
        nonVersionEntity.setChildEntity(new ChildEntity(nonVersionEntity));
        nonDefaultVersionEntityRepository.save(nonVersionEntity);
        childEntityRepository.save(nonVersionEntity.getChildEntity());
    }

    @Test
    public void test_registerEntity_isEqualWithReturned() {
        NonDefaultVersionEntity nonVersionEntity = new NonDefaultVersionEntity();
        nonVersionEntity.setValue("NonDefaultVersionEntity");
        nonVersionEntity.setChildEntity(new ChildEntity(nonVersionEntity));
        NonDefaultVersionEntity returnedEntity = nonDefaultVersionEntityRepository.save(nonVersionEntity);
        childEntityRepository.save(nonVersionEntity.getChildEntity());
        assertThat(nonVersionEntity).isEqualTo(returnedEntity);
    }

    @Test
    public void test_registerEntity_isNotEqualWithReturned() {
        DefaultVersionEntity versionEntity = new DefaultVersionEntity();
        versionEntity.setValue("DefaultVersionEntity");
        versionEntity.setChildEntity(new ChildEntity(versionEntity));
        DefaultVersionEntity returnedEntity = defaultVersionEntityRepository.save(versionEntity);
        assertThat(versionEntity).isNotEqualTo(returnedEntity);
    }
}

interface DefaultVersionEntityRepository extends JpaRepository<DefaultVersionEntity, Long> {

}

interface NonDefaultVersionEntityRepository extends JpaRepository<NonDefaultVersionEntity, Long> {

}

interface ChildEntityRepository extends JpaRepository<ChildEntity, Long> {

}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_DEFAULT_VERSION")
class DefaultVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "VALUE")
    private String value;

    @OneToOne(mappedBy = "defaultVersionEntity", cascade = CascadeType.ALL)
    private ChildEntity childEntity;

    @Version
    private Long versionNo = 0L;
}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_NON_DEFAULT_VERSION")
class NonDefaultVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "VALUE")
    private String value;

    @OneToOne(mappedBy = "nonDefaultVersionEntity", cascade = CascadeType.ALL)
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

    public ChildEntity(DefaultVersionEntity defaultVersionEntity) {
        this.defaultVersionEntity = defaultVersionEntity;
    }

    public ChildEntity(NonDefaultVersionEntity nonDefaultVersionEntity) {
        this.nonDefaultVersionEntity = nonDefaultVersionEntity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private DefaultVersionEntity defaultVersionEntity;

    @OneToOne
    private NonDefaultVersionEntity nonDefaultVersionEntity;
}