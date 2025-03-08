package action.in.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class ActionInBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActionInBlogApplication.class, args);
	}

}
