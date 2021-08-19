package blog.in.action.paging;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

@SpringBootTest
public class JpaPagingTest {

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    public void beforeEach() {
        testRepository.deleteAll();
        for (int index = 0; index < 1000; index++) {
            testRepository.save(new TestEntity(UUID.randomUUID() + "-" + index));
        }
    }

    @Test
    public void test_findBy() {
        Pageable pageable = PageRequest.of(0, 100, Sort.by(Direction.DESC, "createdAt"));
        Page<TestEntity> testEntities = testRepository.findAll(pageable);
        assertThat(testEntities.getTotalElements()).isEqualTo(1000);
        assertThat(testEntities.getTotalPages()).isEqualTo(10);
        assertThat(testEntities.getTotalPages()).isEqualTo(10);
    }
}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_TABLE")
class TestEntity {

    public TestEntity(String value) {
        this.value = value;
    }

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}

interface TestRepository extends JpaRepository<TestEntity, Long> {

}