package blog.in.action;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;

@Log4j2
@SpringBootTest
public class PrePersistUpdateTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void test_prePersist_createdAtIsNotNull() {
        Book book = new Book();
        log.info("before save");
        bookRepository.save(book);
        log.info("after save");
        assertThat(book.getCreateAt()).isNotNull();
    }

    @Test
    public void test_preUpdate() throws InterruptedException {
        Book book = new Book();
        log.info("before first save");
        Book returnedBook = bookRepository.save(book);
        log.info("after first save");
        assertThat(book).isEqualTo(returnedBook);
        book.setTitle("CHANGED");
        Thread.sleep(1000L);
        log.info("before second save");
        returnedBook = bookRepository.save(book);
        log.info("after second save");
        assertThat(book).isNotEqualTo(returnedBook);
        assertThat(book.getCreateAt()).isEqualTo(book.getLastUpdatedAt());
        assertThat(returnedBook.getCreateAt()).isNotEqualTo(returnedBook.getLastUpdatedAt());
    }
}

@Log4j2
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
class Base {

    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    private LocalDateTime createAt;

    @Column(name = "LAST_UPDATED_AT", nullable = false)
    private LocalDateTime lastUpdatedAt;

    public void prePersist() {
        log.info("prePersist");
        LocalDateTime now = LocalDateTime.now();
        createAt = now;
        lastUpdatedAt = now;
    }

    public void preUpdate() {
        log.info("preUpdate");
        lastUpdatedAt = LocalDateTime.now();
    }
}

@Getter
@Setter
@NoArgsConstructor
@Entity
class Book extends Base {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DEFAULT_VALUE")
    private String defaultValue;

    @Override
    @PrePersist
    public void prePersist() {
        super.prePersist();
        defaultValue = defaultValue == null ? "DEFAULT" : defaultValue;
    }

    @Override
    @PreUpdate
    public void preUpdate() {
        super.preUpdate();
    }
}

interface BookRepository extends JpaRepository<Book, Long> {

}