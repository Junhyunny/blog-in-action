package blog.in.action.domain.post;

import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class PostService {

    private final PostRepository repo;

    public PostService(PostRepository repo) {
        this.repo = repo;
    }

    public Post registerPost(Post entity) {
        return repo.save(entity);
    }

    public Post updatePost(Post entity) {
        return repo.save(entity);
    }

    public Page<Post> findByTitle(String postTitle, Pageable pageable) {
        Example<Post> example = Example.of(new Post(postTitle));
        return repo.findAll(example, pageable);
    }

    @Transactional
    public void test(Long postId, int waitingTime) throws InterruptedException {
        long start = System.currentTimeMillis();
        Optional<Post> option = repo.findByIdForUpdate(postId);
        if (!option.isPresent()) {
            throw new RuntimeException("포스트를 찾을 수 없습니다.");
        }
        log.info("포스트 조회에 걸린 시간: " + (System.currentTimeMillis() - start) + "ms");
        Post post = option.get();
        post.setContents("JPA는 어떤 방식으로 Pessimitic Lock을 제공하는지 정리하였습니다. " + Thread.currentThread().getName() + "에 의해 업데이트되었습니다.");
        updatePost(post);
        log.info(waitingTime + "ms 동안 대기합니다.");
        Thread.sleep(waitingTime);
    }
}