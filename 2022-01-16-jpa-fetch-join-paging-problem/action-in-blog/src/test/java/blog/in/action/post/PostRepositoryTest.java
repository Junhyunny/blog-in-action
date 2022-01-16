package blog.in.action.post;

import blog.in.action.reply.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(
        properties = {"spring.jpa.properties.hibernate.default_batch_fetch_size=1000"}
)
public class PostRepositoryTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private PostRepository postRepository;

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
            postRepository.save(post);
            insertReply(post, index + "-reply-");
        }

        em.flush();
        em.clear();
    }

    @Test
    public void whenFindByContentLikeFetchJoin_thenOutOfMemoryWarningMessage() {

        Pageable pageable = PageRequest.of(1, 5);

        Page<Post> postPage = postRepository.findByContentLikeFetchJoin("post", pageable);

        List<Post> posts = postPage.getContent();
        Set<Reply> replies = posts.stream()
                .map(post -> post.getReplies())
                .flatMap(repliesStream -> repliesStream.stream())
                .collect(Collectors.toSet());

        assertThat(replies.size()).isEqualTo(50);
    }

    @Test
    public void whenFindByContentLikeInnerJoin_thenSeeLimitKeywordButNPlusOne() {

        Pageable pageable = PageRequest.of(1, 5);

        Page<Post> postPage = postRepository.findByContentLikeInnerJoin("post", pageable);

        List<Post> posts = postPage.getContent();
        Set<Reply> replies = posts.stream()
                .map(post -> post.getReplies())
                .flatMap(repliesStream -> repliesStream.stream())
                .collect(Collectors.toSet());

        assertThat(replies.size()).isEqualTo(50);
    }

    @Test
    public void whenFindByContentLikeEntityGraph_thenPagingWithInQuery() {

        Pageable pageable = PageRequest.of(1, 5);

        Page<Post> postPage = postRepository.findByContentLikeEntityGraph("post", pageable);

        List<Post> posts = postPage.getContent();
        Set<Reply> replies = posts.stream()
                .map(post -> post.getReplies())
                .flatMap(repliesStream -> repliesStream.stream())
                .collect(Collectors.toSet());

        assertThat(replies.size()).isEqualTo(50);
    }

    @Test
    public void whenFindByContentLike_thenPagingWithInQuery() {

        Pageable pageable = PageRequest.of(1, 5);

        Page<Post> postPage = postRepository.findByContentLike("post", pageable);

        List<Post> posts = postPage.getContent();
        Set<Reply> replies = posts.stream()
                .map(post -> post.getReplies())
                .flatMap(repliesStream -> repliesStream.stream())
                .collect(Collectors.toSet());

        assertThat(replies.size()).isEqualTo(50);
    }
}
