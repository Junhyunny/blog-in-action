package blog.in.action.post;

import blog.in.action.reply.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setup() {

        Post post = Post.builder()
                .title("first post")
                .content("this is the first post.")
                .build();

        postRepository.save(post);

        for (int index = 0; index < 10; index++) {
            Reply reply = Reply.builder()
                    .content("reply-" + index)
                    .post(post)
                    .build();
            post.addReply(reply);
            em.persist(reply);
        }

        em.flush();
        em.clear();
    }

    @Test
    public void givenFindPost_whenGetReplies_thenSizeIsZero() {

        Post post = postRepository.findTopByTitle("first post");

        List<String> replyContents = post.getReplies()
                .stream()
                .map(reply -> reply.getContent())
                .collect(Collectors.toList());

        assertThat(replyContents.size()).isEqualTo(10);
    }
}
