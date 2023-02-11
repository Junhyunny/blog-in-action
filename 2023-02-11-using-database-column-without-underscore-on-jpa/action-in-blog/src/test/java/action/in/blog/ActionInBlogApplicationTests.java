package action.in.blog;

import action.in.blog.domain.Post;
import action.in.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DataJpaTest
@TestPropertySource(
        properties = {
                "spring.sql.init.mode=embedded",
                "spring.sql.init.schema-locations=classpath:db/schema.sql",
                "spring.jpa.hibernate.ddl-auto=none",
                "spring.jpa.defer-datasource-initialization=true"
        }
)
class ActionInBlogApplicationTests {

    @Autowired
    PostRepository postRepository;

    @Test
    void select_post_by_id() {
        Post post = Post.builder()
                .title("first post")
                .type("essay")
                .content("this is my first essay.")
                .build();
        postRepository.save(post);
        postRepository.flush();


        Post result = postRepository.findFirstByTitle("first post");


        assertThat(result.getTitle(), equalTo("first post"));
        assertThat(result.getType(), equalTo("essay"));
        assertThat(result.getContent(), equalTo("this is my first essay."));
    }
}
