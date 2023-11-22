package action.in.blog.service;

import action.in.blog.repository.CardLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Component
class AsyncTransaction {

    @Transactional
    public void run(Runnable runnable) {
        runnable.run();
    }
}

@Import(AsyncTransaction.class)
@DataJpaTest(properties = {
        "spring.sql.init.data-locations=classpath:db/data.sql",
        "spring.jpa.defer-datasource-initialization=true"
})
class CardLikeServiceTest {

    @Autowired
    AsyncTransaction asyncTransaction;
    @Autowired
    CardLikeRepository cardLikeRepository;
    CardLikeService sut;

    @BeforeEach
    void setUp() {
        sut = new DefaultCardLikeService(cardLikeRepository);
    }

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void insert_same_data_in_the_same_time_then_throw_sql_exception() {

        var throwable = assertThrows(RuntimeException.class, () -> {
            var tx1 = CompletableFuture.runAsync(() -> asyncTransaction.run(() -> sut.increaseLikeCount("card-02")));
            var tx2 = CompletableFuture.runAsync(() -> asyncTransaction.run(() -> sut.increaseLikeCount("card-02")));
            tx1.join();
            tx2.join();
        });


        assertInstanceOf(DataIntegrityViolationException.class, throwable.getCause());
    }

    @Test
    void other_transaction_exists_then_throw_timeout_exception() {

        var tx1 = CompletableFuture.runAsync(() -> asyncTransaction.run(() -> {
            sut.increaseLikeCount("card-01");
            sleep(7000);
        }));
        sleep(1000);


        var throwable = assertThrows(RuntimeException.class, () -> sut.increaseLikeCount("card-01"));


        tx1.join();
        assertInstanceOf(PessimisticLockingFailureException.class, throwable);
    }
}
