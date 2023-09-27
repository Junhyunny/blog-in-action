package action.in.blog.service;

import action.in.blog.domain.Post;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DefaultPostService implements PostService {

    public DefaultPostService() {
        log.info("create DefaultPostService");
    }

    @Override
    public List<Post> getPosts() {
        return List.of(
                new Post(1L, "Hello World", "This is content."),
                new Post(2L, "Junhyunny's Devlog", "This is blog.")
        );
    }

    @Override
    public final void createPost(Post post) {
        log.info("create new post {}", post);
    }
}
