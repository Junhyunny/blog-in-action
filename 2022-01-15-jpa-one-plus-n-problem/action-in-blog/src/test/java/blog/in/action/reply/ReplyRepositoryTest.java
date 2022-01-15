package blog.in.action.reply;

import blog.in.action.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class ReplyRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ReplyRepository replyRepository;

    @BeforeEach
    public void setup() {

        Post post = Post.builder()
                .title("first post")
                .content("this is the first post.")
                .build();

        em.persist(post);

        for (int index = 0; index < 10; index++) {
            Reply reply = Reply.builder()
                    .content("reply-" + index)
                    .post(post)
                    .build();
            post.addReply(reply);

            replyRepository.save(reply);
        }

        em.flush();
        em.clear();
    }

    @Test
    public void givenFindByContent_whenGetPost_thenNPlusOneQuery() {

        Reply reply = replyRepository.findByContent("reply-0");

        Post post = reply.getPost();

        assertThat(post.getTitle()).isEqualTo("first post");
    }

    @Test
    public void whenFindByContentFetchJoin_thenJustOneQuery() {

        Reply reply = replyRepository.findByContentFetchJoin("reply-0");

        Post post = reply.getPost();

        assertThat(post.getTitle()).isEqualTo("first post");
    }

    @Test
    public void whenFindByContentEntityGraph_thenJustOneQuery() {

        Reply reply = replyRepository.findByContentEntityGraph("reply-0");

        Post post = reply.getPost();

        assertThat(post.getTitle()).isEqualTo("first post");
    }
}