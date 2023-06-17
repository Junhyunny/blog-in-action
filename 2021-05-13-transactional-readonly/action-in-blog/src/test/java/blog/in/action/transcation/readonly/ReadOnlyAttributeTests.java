package blog.in.action.transcation.readonly;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static blog.in.action.transcation.readonly.PostState.ARCHIVE;
import static blog.in.action.transcation.readonly.PostState.STAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

enum PostState {
    STAGE,
    ARCHIVE
}

interface PostRepository extends JpaRepository<Post, Long> {

    Post findByContent(String content);
}

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String content;
    @Enumerated(value = EnumType.STRING)
    private PostState state;

    public void archive() {
        state = ARCHIVE;
    }
}

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class PostService {

    private final PostRepository repository;

    public void create(Post post) {
        repository.save(post);
    }

    public void update(Post post) {
        repository.save(post);
    }

    public void archive(long id) {
        var post = repository.findById(id).orElseThrow();
        post.archive();
    }
}

@SpringBootTest
@TestPropertySource(
        properties = {
                "spring.jpa.show-sql=true",
                "spring.jpa.hibernate.ddl-auto=create",
                "spring.datasource.url=jdbc:tc:mysql:8.0.32:///test",
                "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
        }
)
@Testcontainers
public class ReadOnlyAttributeTests {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.32").withDatabaseName("test");

    @Autowired
    PostService sut;

    @Autowired
    PostRepository repository;

    @BeforeEach
    void beforeEach() {
        repository.deleteAll();
    }

    @Test
    void throw_exception_when_create_in_readonly() {

        JpaSystemException throwable = assertThrows(JpaSystemException.class, () -> {
            sut.create(
                    Post.builder()
                            .content("Hello World")
                            .build()
            );
        });


        var result = repository.findByContent("Hello World");
        assertThat(result, equalTo(null));
        assertThat(throwable.getRootCause().getMessage(), equalTo("Connection is read-only. Queries leading to data modification are not allowed"));
    }

    @Test
    void nothing_change_when_update_in_readonly() {

        var post = Post.builder()
                .content("Hello World")
                .build();
        repository.saveAndFlush(post);


        sut.update(
                Post.builder()
                        .id(post.getId())
                        .content("This is new world")
                        .build()
        );


        var result = repository.findById(post.getId()).orElseThrow();
        assertThat(result.getContent(), equalTo("Hello World"));
    }

    @Test
    void nothing_change_when_dirty_check_in_readonly() {

        var post = Post.builder()
                .state(STAGE)
                .build();
        repository.saveAndFlush(post);


        sut.archive(post.getId());


        var result = repository.findById(post.getId()).orElseThrow();
        assertThat(result.getState(), equalTo(STAGE));
    }
}