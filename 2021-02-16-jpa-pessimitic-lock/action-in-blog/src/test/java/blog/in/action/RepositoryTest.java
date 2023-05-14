package blog.in.action;

import lombok.extern.log4j.Log4j2;
import org.h2.jdbc.JdbcSQLTimeoutException;
import org.hibernate.PessimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

interface PostRepository extends JpaRepository<Post, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Post findByTitle(String title);
}

@Component
class AsyncTransaction {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void run(Runnable runnable) {
        runnable.run();
    }
}

@Log4j2
@Import(AsyncTransaction.class)
@DataJpaTest
@TestPropertySource(
        properties = {
                "spring.sql.init.mode=embedded",
                "spring.sql.init.schema-locations=classpath:db/schema.sql",
                "spring.sql.init.data-locations=classpath:db/data.sql",
                "spring.jpa.defer-datasource-initialization=true"
        }
)
public class RepositoryTest {

    @Autowired
    private AsyncTransaction asyncTransaction;
    @Autowired
    private PostRepository postRepository;

    void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void pessimistic_lock_with_repository() {
        CompletableFuture<Void> tx = CompletableFuture.runAsync(() -> asyncTransaction.run(() -> {
            Post post = postRepository.findByTitle("Hello World");
            post.setContents("This is tx1.");
            log.info("This is tx1 before sleep");
            sleep(7000);
            log.info("This is tx1 after sleep");
        }));
        sleep(500);
        Throwable throwable = assertThrows(Exception.class, () -> {
            CompletableFuture.runAsync(() -> asyncTransaction.run(() -> {
                Post post = postRepository.findByTitle("Hello World");
                post.setContents("This is tx2.");
                log.info("This is tx2");
            })).join();
        });
        tx.join();


        Throwable pessimisticLockingFailure = throwable.getCause();
        Throwable pessimisticLock = pessimisticLockingFailure.getCause();
        Throwable jdbcSQLTimeout = pessimisticLock.getCause();
        Post result = postRepository.findByTitle("Hello World");

        assertThat(throwable, instanceOf(CompletionException.class));
        assertThat(pessimisticLockingFailure, instanceOf(PessimisticLockingFailureException.class));
        assertThat(pessimisticLock, instanceOf(PessimisticLockException.class));
        assertThat(jdbcSQLTimeout, instanceOf(JdbcSQLTimeoutException.class));
        assertThat(result.getContents(), equalTo("This is tx1."));
    }
}
