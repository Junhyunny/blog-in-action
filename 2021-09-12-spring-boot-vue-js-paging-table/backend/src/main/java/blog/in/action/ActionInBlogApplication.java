package blog.in.action;

import blog.in.action.entity.Post;
import blog.in.action.repository.PostRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActionInBlogApplication implements CommandLineRunner {

    private final PostRepository repository;

    public ActionInBlogApplication(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        repository.deleteAll();
        List<Post> postList = new ArrayList<>();
        for (int index = 0; index < 100; index++) {
            postList.add(Post.builder()
                .title("title-" + index)
                .contents("contents-" + (99 - index))
                .build());
        }
        repository.saveAll(postList);
    }

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }
}
