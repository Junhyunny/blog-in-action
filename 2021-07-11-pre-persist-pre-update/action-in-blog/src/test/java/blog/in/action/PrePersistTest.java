package blog.in.action;

import blog.in.action.domain.BookEntity;
import blog.in.action.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase
@SpringBootTest
public class PrePersistTest {

    @Autowired
    EntityManager entityManager;
    @Autowired
    BookRepository sut;

    @Test
    void save_createdAtAndUpdatedAtIsNotNull() {
        BookEntity book = BookEntity.builder()
                .title("JPA 101")
                .isbn(UUID.randomUUID().toString())
                .build();


        sut.save(book);


        assertNotNull(book.getCreateAt());
        assertNotNull(book.getUpdatedAt());
        assertEquals(book.getCreateAt(), book.getUpdatedAt());
    }

    @Test
    void isbnIsNull_save_throwException() {
        BookEntity book = BookEntity.builder()
                .title("JPA 201")
                .build();


        RuntimeException throwable = assertThrows(RuntimeException.class, () -> sut.save(book));
        assertEquals(throwable.getMessage(), "isbn must be not null");
        TypedQuery<BookEntity> query = entityManager.createQuery(
                "select b from BookEntity b where b.title=:title",
                BookEntity.class
        );
        query.setParameter("title", "JPA 201");
        List<BookEntity> result = query.getResultList();
        assertEquals(0, result.size());
    }
}
