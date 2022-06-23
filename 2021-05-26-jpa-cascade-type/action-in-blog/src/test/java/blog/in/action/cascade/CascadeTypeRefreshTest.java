package blog.in.action.cascade;

import static org.assertj.core.api.Assertions.assertThat;
import blog.in.action.cascade.entity.Comment;
import blog.in.action.cascade.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceUnit;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class CascadeTypeRefreshTest {

    @PersistenceUnit
    private EntityManagerFactory factory;

    @BeforeEach
    public void beforeEach() {
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

    @Test
    public void test_refresh() {
        EntityManager em = factory.createEntityManager();
        try {
            em.getTransaction().begin();
            String findPost = "SELECT * FROM TB_POST WHERE TITLE ='Title at test'";
            String updateComment = "UPDATE TB_COMMENT SET COMMENT = 'TEST' WHERE POST_ID = ";
            Post post = (Post) em.createNativeQuery(findPost, Post.class).getSingleResult();
            Comment comment = post.getCommentList().get(0);
            em.createNativeQuery(updateComment + post.getId()).executeUpdate();
            assertThat(comment.getComment()).isNotEqualTo("TEST");
            log.info("========= comment before refresh: " + comment.getComment());
            em.refresh(post);
            log.info("========= comment after refresh: " + comment.getComment());
            assertThat(comment.getComment()).isEqualTo("TEST");
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            log.error("exception occurs", ex);
        } finally {
            em.close();
        }
    }
}
