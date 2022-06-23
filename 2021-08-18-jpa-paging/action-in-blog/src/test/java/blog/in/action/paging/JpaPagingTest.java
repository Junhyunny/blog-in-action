package blog.in.action.paging;

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
import lombok.extern.log4j.Log4j2;
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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Log4j2
@SpringBootTest
public class JpaPagingTest {

    @Autowired
    private TestRepository testRepository;

    @BeforeEach
    public void beforeEach() {
        testRepository.deleteAll();
        for (int index = 0; index < 250; index++) {
            testRepository.save(new TestEntity(UUID.randomUUID() + "-" + index));
        }
    }

    @Test
    public void testUsingFindAll() {
        for (int pageIndex = 0; pageIndex < 2; pageIndex++) {
            Pageable pageable = PageRequest.of(pageIndex, 10, Sort.by(Direction.DESC, "testValue"));
            Page<TestEntity> testEntities = testRepository.findAll(pageable);
            log.info("전체 페이지 수: " + testEntities.getTotalPages());
            log.info("현재 페이지 번호: " + testEntities.getPageable().getPageNumber());
            log.info("페이지 별 사이즈: " + testEntities.getPageable().getPageSize());
            log.info("조건 일치 총 항목 수: " + testEntities.getTotalElements());
            testEntities.forEach(testEntity -> {
                log.info(testEntity);
            });
        }
    }

    @Test
    public void testUsingFindBy() {
        for (int pageIndex = 0; pageIndex < 2; pageIndex++) {
            Pageable pageable = PageRequest.of(pageIndex, 10, Sort.by(Direction.DESC, "testValue"));
            Page<TestEntity> testEntities = testRepository.findByTestValueStartsWith("A", pageable);
            log.info("전체 페이지 수: " + testEntities.getTotalPages());
            log.info("현재 페이지 번호: " + testEntities.getPageable().getPageNumber());
            log.info("페이지 별 사이즈: " + testEntities.getPageable().getPageSize());
            log.info("조건 일치 총 항목 수: " + testEntities.getTotalElements());
            testEntities.forEach(testEntity -> {
                log.info(testEntity);
            });
        }
    }

    @Test
    public void testUsingJpql() {
        for (int pageIndex = 0; pageIndex < 2; pageIndex++) {
            Pageable pageable = PageRequest.of(pageIndex, 10, Sort.by(Direction.DESC, "testValue"));
            Page<TestEntity> testEntities = testRepository.findByValueStartsWithUsingJpql("A", pageable);
            log.info("전체 페이지 수: " + testEntities.getTotalPages());
            log.info("현재 페이지 번호: " + testEntities.getPageable().getPageNumber());
            log.info("페이지 별 사이즈: " + testEntities.getPageable().getPageSize());
            log.info("조건 일치 총 항목 수: " + testEntities.getTotalElements());
            testEntities.forEach(testEntity -> {
                log.info(testEntity);
            });
        }
    }

    @Test
    public void testUsingNative() {
        for (int pageIndex = 0; pageIndex < 2; pageIndex++) {
            Pageable pageable = PageRequest.of(pageIndex, 10, Sort.by(Direction.DESC, "TEST_VALUE"));
            Page<TestEntity> testEntities = testRepository.findByValueStartsWithUsingNative("A", pageable);
            log.info("전체 페이지 수: " + testEntities.getTotalPages());
            log.info("현재 페이지 번호: " + testEntities.getPageable().getPageNumber());
            log.info("페이지 별 사이즈: " + testEntities.getPageable().getPageSize());
            log.info("조건 일치 총 항목 수: " + testEntities.getTotalElements());
            testEntities.forEach(testEntity -> {
                log.info(testEntity);
            });
        }
    }
}

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_TABLE")
class TestEntity {

    public TestEntity(String value) {
        this.testValue = value;
    }

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "TEST_VALUE")
    private String testValue;

    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "id: " + id + ", testValue: " + testValue + ", createdAt: " + createdAt;
    }
}

interface TestRepository extends JpaRepository<TestEntity, Long> {

    Page<TestEntity> findByTestValueStartsWith(String testValue, Pageable pageable);

    @Query(value = "SELECT t FROM TestEntity t WHERE t.testValue LIKE :testValue%", countQuery = "SELECT COUNT(t) FROM TestEntity t WHERE t.testValue LIKE :testValue%")
    Page<TestEntity> findByValueStartsWithUsingJpql(@Param("testValue") String testValue, Pageable pageable);

    @Query(value = "SELECT * FROM TB_TABLE t WHERE TEST_VALUE LIKE :testValue%", countQuery = "SELECT COUNT(*) FROM TB_TABLE t WHERE t.TEST_VALUE LIKE :testValue%", nativeQuery = true)
    Page<TestEntity> findByValueStartsWithUsingNative(@Param("testValue") String testValue, Pageable pageable);
}