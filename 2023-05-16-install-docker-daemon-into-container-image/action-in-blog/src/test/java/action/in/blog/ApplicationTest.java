package action.in.blog;

import action.in.blog.domain.Post;
import action.in.blog.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Testcontainers
class ApplicationTest {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.32").withDatabaseName("test");

    @Autowired
    PostRepository sut;

    @Test
    void find_posts_by_title_containing_with_trim() {
        sut.save(
                Post.builder()
                        .title("Hello World")
                        .content("This is sample content")
                        .build()
        );


        List<Post> result = sut.findByTitleContainsWithTrim("       Hello World       ");


        Post firstPost = result.get(0);
        assertThat(result.size(), equalTo(1));
        assertThat(firstPost.getTitle(), equalTo("Hello World"));
        assertThat(firstPost.getContent(), equalTo("This is sample content"));
    }
}