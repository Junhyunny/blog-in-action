package blog.in.action.cascade;

import blog.in.action.cascade.entity.Comment;
import blog.in.action.cascade.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class CascadeTypePersistTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @Test
    public void test_persist() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            String deleteComment = "DELETE FROM TB_COMMENT";
            String deletePost = "DELETE FROM TB_POST";
            em.createNativeQuery(deleteComment).executeUpdate();
            em.createNativeQuery(deletePost).executeUpdate();
            Post post = new Post();
            post.setTitle("Title at test");
            post.setContents("Contents at test");
            List<Comment> commentList = new ArrayList<>();
            for (int index = 0; index < 3; index++) {
                Comment comment = new Comment();
                comment.setComment("Comment at test, " + index);
                comment.setPost(post);
                commentList.add(comment);
            }
            post.setCommentList(commentList);
            em.persist(post);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.error("exception occurs", ex);
        } finally {
            em.close();
        }
    }
}
