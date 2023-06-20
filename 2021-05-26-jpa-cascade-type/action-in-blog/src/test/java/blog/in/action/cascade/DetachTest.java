package blog.in.action.cascade;

import blog.in.action.cascade.entity.Comment;
import blog.in.action.cascade.entity.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class DetachTest {

    @PersistenceContext
    private EntityManager sut;

    @BeforeEach
    public void beforeEach() {
        var comment = Comment.builder()
                .content("This is comment")
                .build();
        var comments = Collections.singletonList(comment);
        var post = Post.builder()
                .content("Hello World")
                .comments(comments)
                .build();
        sut.persist(post);
        sut.persist(comment);
        sut.flush();
        sut.clear();
    }

    @Test
    public void cascade_when_detach() {

        String findByContent = "SELECT p FROM Post p where p.content = ?1";
        TypedQuery<Post> selectQuery = sut.createQuery(findByContent, Post.class);
        selectQuery.setParameter(1, "Hello World");
        var post = selectQuery.getSingleResult();
        var comment = post.getComments().get(0);
        var commentId = comment.getId();


        comment.setContent("This is new comment");
        sut.detach(post);
        sut.flush();
        sut.clear();


        var resultComment = sut.find(Comment.class, commentId);
        assertThat(resultComment.getContent(), equalTo("This is comment"));
    }
}
