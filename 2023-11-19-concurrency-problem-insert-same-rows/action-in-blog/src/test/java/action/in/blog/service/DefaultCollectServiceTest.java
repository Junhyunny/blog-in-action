package action.in.blog.service;

import action.in.blog.exception.DuplicatedCollectException;
import action.in.blog.repository.CollectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
class DefaultCollectServiceTest {

    @Autowired
    CollectRepository collectRepository;
    CollectService sut;

    @BeforeEach
    void setUp() {
        sut = new DefaultCollectService(collectRepository);
    }

    @Test
    void throwDuplicatedCollectException() {

        assertThrows(DuplicatedCollectException.class, () -> {
            sut.collect("junhyunny", "card-01");
            try {
                CompletableFuture
                        .runAsync(() -> sut.collect("junhyunny", "card-01"))
                        .join();
            } catch (CompletionException e) {
                throw e.getCause();
            }
        });
    }
}