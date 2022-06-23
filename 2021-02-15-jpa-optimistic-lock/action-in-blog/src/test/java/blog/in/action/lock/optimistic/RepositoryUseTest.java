package blog.in.action.lock.optimistic;

import blog.in.action.domain.post.Post;
import blog.in.action.domain.post.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Log4j2
@SpringBootTest
public class RepositoryUseTest {

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    private void beforeEach() {
        Page<Post> page = postRepository.findByTitle("Optimistic Lock", PageRequest.of(0, 10, Sort.by(Direction.DESC, "title")));
        if (page.isEmpty()) {
            Post post = new Post();
            post.setTitle("Optimistic Lock");
            post.setContents("JPA는 어떤 방식으로 Optimistic Lock을 제공하는지 정리하였습니다.");
            post.setVersionNo(Long.valueOf(0L));
            postRepository.save(post);
        } else {
            Post post = page.getContent().get(0);
            post.setContents("JPA는 어떤 방식으로 Optimistic Lock을 제공하는지 정리하였습니다.");
            postRepository.save(post);
        }
    }

    @Test
    public void test() {
        Page<Post> page = postRepository.findByTitle("Optimistic Lock", PageRequest.of(0, 10, Sort.by(Direction.DESC, "title")));
        if (!page.isEmpty()) {
            Post post = page.getContent().get(0);
            Thread tx1 = new Thread(new UpdatePostTask(post.getId(), 1100));
            Thread tx2 = new Thread(new UpdatePostTask(post.getId(), 1000));
            tx1.setName("1.1 초 대기 스레드");
            tx2.setName("1.0 초 대기 스레드");
            tx1.start();
            tx2.start();
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                log.error("main thread sleep error", e);
            }
        }
    }

    private class UpdatePostTask implements Runnable {

        private Long postId;

        private Integer waitingTime;

        public UpdatePostTask(Long postId, Integer waitingTime) {
            this.postId = postId;
            this.waitingTime = waitingTime;
        }

        @Override
        public void run() {
            Post post = null;
            try {
                post = postRepository.findById(postId).get();
                post.setContents("JPA는 어떤 방식으로 Optimistic Lock을 제공하는지 정리하였습니다. " + Thread.currentThread().getName() + "에 의해 업데이트되었습니다.");
                Thread.sleep(waitingTime);
                postRepository.save(post);
            } catch (OptimisticLockingFailureException optEx) {
                log.error(post.getTitle() + " 포스트는 다른 트랜잭션에 의해 업데이트되었습니다.", optEx);
            } catch (Exception e) {
                log.error("update thread sleep error", e);
            }
        }
    }
}