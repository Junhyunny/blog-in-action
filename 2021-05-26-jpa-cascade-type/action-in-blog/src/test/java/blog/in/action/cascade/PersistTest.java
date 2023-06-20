package blog.in.action.cascade;

import blog.in.action.cascade.entity.Comment;
import blog.in.action.cascade.entity.Post;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import jakarta.persistence.EntityManager;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class PersistTest {

    @PersistenceContext
    private EntityManager sut;

    @Test
    public void cascade_when_persist() {

        var comment = Comment.builder()
                .content("This is comment")
                .build();
        var comments = Collections.singletonList(comment);
        var post = Post.builder()
                .content("Hello World")
                .comments(comments)
                .build();


        sut.persist(post);
        sut.flush();
        sut.clear();


        var postId = post.getId();
        var commentId = comment.getId();
        var resultPost = sut.find(Post.class, postId);
        var resultComment = sut.find(Comment.class, commentId);
        assertThat(resultPost.getContent(), equalTo("Hello World"));
        assertThat(resultComment.getContent(), equalTo("This is comment"));
    }
}
