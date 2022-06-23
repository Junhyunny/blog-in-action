package blog.in.action.lock.optimistic;

import blog.in.action.domain.post.Post;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class EntityManagerUseTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @BeforeEach
    private void beforeEach() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Post> query = em.createQuery("select p from Post p where p.title = :title", Post.class);
            query.setParameter("title", "Optimistic Lock");
            Post post = query.getSingleResult();
            if (post == null) {
                post = new Post();
                post.setTitle("Optimistic Lock");
                post.setContents("JPA는 어떤 방식으로 Optimistic Lock을 제공하는지 정리하였습니다.");
                post.setVersionNo(Long.valueOf(0L));
                em.persist(post);
            } else {
                post.setContents("JPA는 어떤 방식으로 Optimistic Lock을 제공하는지 정리하였습니다.");
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            log.error("beforeEach thread error", e);
            em.getTransaction().rollback();
        }
    }

    @Test
    public void test() {
        EntityManager em = factory.createEntityManager();
        TypedQuery<Post> query = em.createQuery("select p from Post p where p.title = :title", Post.class);
        query.setParameter("title", "Optimistic Lock");
        Post post = query.getSingleResult();
        if (post != null) {
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
            EntityManager em = factory.createEntityManager();
            Post post = null;
            try {
                em.getTransaction().begin();
                post = em.find(Post.class, postId);
                post.setContents("JPA는 어떤 방식으로 Optimistic Lock을 제공하는지 정리하였습니다. " + Thread.currentThread().getName() + "에 의해 업데이트되었습니다.");
                // em.lock(post, LockModeType.OPTIMISTIC);
                Thread.sleep(waitingTime);
                em.getTransaction().commit();
            } catch (RollbackException rollbackEx) {
                log.error(post.getTitle() + " 포스트는 다른 트랜잭션에 의해 업데이트되었습니다.", rollbackEx);
                em.getTransaction().rollback();
            } catch (Exception e) {
                log.error("update thread sleep error", e);
                em.getTransaction().rollback();
            }
        }
    }
}
