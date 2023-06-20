package blog.in.action.cascade;

import blog.in.action.cascade.entity.Comment;
import blog.in.action.cascade.entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
public class RemoveTest {

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
    public void cascade_when_remove() {

        String findByContent = "SELECT p FROM Post p where p.content = ?1";
        TypedQuery<Post> selectQuery = sut.createQuery(findByContent, Post.class);
        selectQuery.setParameter(1, "Hello World");
        var post = selectQuery.getSingleResult();
        var postId = post.getId();
        var commentId = post.getComments()
                .get(0)
                .getId();


        sut.remove(post);
        sut.flush();
        sut.clear();


        var resultPost = sut.find(Post.class, postId);
        var resultComment = sut.find(Comment.class, commentId);
        assertThat(resultPost == null, equalTo(true));
        assertThat(resultComment == null, equalTo(true));
    }
}
