package blog.in.action;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

interface PostRepository extends JpaRepository<Post, Long> {

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
    public void optimistic_lock_with_repository() {
        CompletableFuture<Void> tx = CompletableFuture.runAsync(() -> asyncTransaction.run(() -> {
            Post post = postRepository.findByTitle("Hello World");
            post.setContents("This is tx1.");
            sleep(500);
        }));
        Throwable throwable = assertThrows(Exception.class, () -> {
            CompletableFuture.runAsync(() -> asyncTransaction.run(() -> {
                Post post = postRepository.findByTitle("Hello World");
                post.setContents("This is tx2.");
                sleep(1000);
            })).join();
        });
        tx.join();


        Post post = postRepository.findByTitle("Hello World");
        assertThat(post.getContents(), equalTo("This is tx1."));
        assertThat(post.getVersionNo(), equalTo(1L));
        assertThat(throwable.getCause(), instanceOf(ObjectOptimisticLockingFailureException.class));
    }
}