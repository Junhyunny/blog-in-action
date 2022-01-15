package blog.in.action.post;

import blog.in.action.reply.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
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

        Post post = getPost("first post", "this is the first post.");
        Post secondPost = getPost("second post", "this is the second post.");

        postRepository.save(post);
        postRepository.save(secondPost);

        insertReply(post, "first-reply-");
        insertReply(secondPost, "second-reply-");

        em.flush();
        em.clear();
    }

    @Test
    public void givenFindAll_whenGetReplies_thenNPlusOneQuery() {

        List<Post> posts = postRepository.findAll();

        List<Reply> replies = posts.stream()
                .map(post -> post.getReplies())
                .flatMap(repliesItems -> repliesItems.stream())
                .collect(Collectors.toList());

        assertThat(replies.size()).isEqualTo(20);
    }

    @Test
    public void givenFindByTitle_whenGetReplies_thenNPlusOneQuery() {

        List<Post> posts = postRepository.findByTitle("first post");

        List<String> replyContents = posts
                .stream()
                .map(post -> post.getReplies())
                .flatMap(replies -> replies.stream())
                .map(reply -> reply.getContent())
                .collect(Collectors.toList());

        assertThat(replyContents.size()).isEqualTo(10);
    }

    @Test
    public void givenFindDistinctByTitleFetchJoin_whenGetReplies_thenJustOneQuery() {

        List<Post> posts = postRepository.findDistinctByTitleFetchJoin("first post");

        List<String> replyContents = posts
                .stream()
                .map(post -> post.getReplies())
                .flatMap(replies -> replies.stream())
                .map(reply -> reply.getContent())
                .collect(Collectors.toList());

        assertThat(replyContents.size()).isEqualTo(10);
    }


    @Test
    public void givenFindByTitleFetchJoin_whenGetReplies_thenJustOneQuery() {

        Set<Post> posts = postRepository.findByTitleFetchJoin("first post");

        List<String> replyContents = posts
                .stream()
                .map(post -> post.getReplies())
                .flatMap(replies -> replies.stream())
                .map(reply -> reply.getContent())
                .collect(Collectors.toList());

        assertThat(replyContents.size()).isEqualTo(10);
    }

    @Test
    public void givenFindDistinctByTitleEntityGraph_whenGetReplies_thenJustOneQuery() {

        List<Post> posts = postRepository.findDistinctByTitleEntityGraph("first post");

        List<String> replyContents = posts
                .stream()
                .map(post -> post.getReplies())
                .flatMap(replies -> replies.stream())
                .map(reply -> reply.getContent())
                .collect(Collectors.toList());

        assertThat(posts.size()).isEqualTo(1);
        assertThat(replyContents.size()).isEqualTo(10);
    }

    @Test
    public void givenFindByTitleEntityGraph_whenGetReplies_thenJustOneQuery() {

        Set<Post> posts = postRepository.findByTitleEntityGraph("first post");

        List<String> replyContents = posts
                .stream()
                .map(post -> post.getReplies())
                .flatMap(replies -> replies.stream())
                .map(reply -> reply.getContent())
                .collect(Collectors.toList());

        assertThat(posts.size()).isEqualTo(1);
        assertThat(replyContents.size()).isEqualTo(10);
    }
}
