package action.in.blog;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ActionInBlogApplicationIT {

    @Test
    void contextLoads() {
        if(true) {
            throw new RuntimeException();
        }
    }

}
