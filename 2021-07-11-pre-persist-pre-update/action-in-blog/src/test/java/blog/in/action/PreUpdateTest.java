package blog.in.action;

import blog.in.action.domain.BookEntity;
import blog.in.action.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PreUpdateTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    BookRepository sut;

    BookEntity book;

    @BeforeEach
    void setUp() {
        book = BookEntity.builder()
                .title("JPA 301")
                .isbn(UUID.randomUUID().toString())
                .build();
        sut.save(book);
    }

    @Test
    void update_updatedAtIsAfterCreatedAt() throws InterruptedException {
        Thread.sleep(100);
        BookEntity givenBook = entityManager.find(BookEntity.class, book.getId());


        sut.save(
                BookEntity.builder()
                        .id(givenBook.getId())
                        .isbn(givenBook.getIsbn())
                        .title("JPA 401")
                        .build()
        );


        BookEntity result = entityManager.find(BookEntity.class, book.getId());
        LocalDateTime createdAt = result.getCreateAt();
        LocalDateTime updatedAt = result.getUpdatedAt();
        assertEquals("JPA 401", result.getTitle());
        assertTrue(updatedAt.isAfter(createdAt));
    }
}
