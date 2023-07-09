package action.in.blog;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@Slf4j
@SpringBootApplication
public class ActionInBlogApplication {

    @Value("${database.user}")
    private String databaseUser;

    @Value("${database.password}")
    private String databasePassword;

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }

    @PostConstruct
    private void postConstructor() {
        log.info("{}", Map.of(
                        "databaseUser", databaseUser,
                        "databasePassword", databasePassword
                )
        );
    }
}
