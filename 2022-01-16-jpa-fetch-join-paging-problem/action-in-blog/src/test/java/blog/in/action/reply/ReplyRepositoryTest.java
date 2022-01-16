package blog.in.action.reply;

import blog.in.action.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReplyRepositoryTest {


    @Autowired
    private EntityManager em;

    @Autowired
    private ReplyRepository replyRepository;

    Post getPost(String title, String content) {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }

    void insertReply(Post post, String content) {
        for (int index = 0; index < 10; index++) {
            Reply reply = Reply.builder()
                    .content(content + index)
                    .post(post)
                    .build();
            post.addReply(reply);
            em.persist(reply);
        }
    }

    @BeforeEach
    public void setup() {

        for (int index = 0; index < 10; index++) {
            Post post = getPost(index + " post", "this is the " + index + " post.");
            em.persist(post);
            insertReply(post, index + "-reply-");
        }

        em.flush();
        em.clear();
    }

    @Test
    public void whenFindByPostIdFetchJoin_thenNothingWarning() {

        Pageable pageable = PageRequest.of(1, 5);

        Page<Reply> replyPage = replyRepository.findByContentLikeFetchJoin("0-reply-", pageable);

        assertThat(replyPage.getContent().size()).isEqualTo(5);
    }

    @Test
    public void whenFindByPostIdInnerJoin_thenNothingWarning() {

        Pageable pageable = PageRequest.of(1, 5);

        Page<Reply> replyPage = replyRepository.findByContentLikeInnerJoin("0-reply-", pageable);

        assertThat(replyPage.getContent().size()).isEqualTo(5);
    }

    @Test
    public void whenFindByPostIdEntityGraph_thenNothingWarning() {

        Pageable pageable = PageRequest.of(1, 5);

        Page<Reply> replyPage = replyRepository.findByContentLikeEntityGraph("0-reply-", pageable);

        assertThat(replyPage.getContent().size()).isEqualTo(5);
    }
}
