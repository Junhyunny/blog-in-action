package action.in.blog;

import org.springframework.boot.SpringApplication;

public class TestActionInBlogApplication {

    public static void main(String[] args) {
        SpringApplication.from(ActionInBlogApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
