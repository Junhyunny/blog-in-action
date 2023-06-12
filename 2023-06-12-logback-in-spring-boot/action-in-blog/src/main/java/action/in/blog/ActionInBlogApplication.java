package action.in.blog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.IntStream;

@Slf4j
@SpringBootApplication
public class ActionInBlogApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ActionInBlogApplication.class, args);
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        IntStream.range(0, Integer.MAX_VALUE)
                .forEach(number -> {
                    log.trace("Hello Trace World {}", number);
                    log.debug("Hello Debug World {}", number);
                    log.info("Hello Info World {}", number);
                    log.warn("Hello Warn World {}", number);
                    log.error("Hello Error World {}", number);
                    sleep(100);
                });
    }
}
