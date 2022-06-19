package blog.in.action.history;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@AutoConfigureTestDatabase
@SpringBootTest
public class AccessHistoryIT {

    @Autowired
    AccessHistoryRepository repository;

    @Autowired
    AccessHistoryService service;

//    @Test
//    void test() {
//        List<String> paths = Arrays.asList("/hello", null, "/world");
//
//        Throwable throwable = assertThrows(UnexpectedRollbackException.class, () -> {
//            service.createAccessHistories(paths, "Junhyunny");
//        });
//        throwable.printStackTrace();
//
//        assertThat(repository.count(), equalTo(0L));
//    }

    @Test
    void test() {
        List<String> paths = Arrays.asList("/hello", null, "/world");

        service.createAccessHistories(paths, "Junhyunny");

        assertThat(repository.count(), equalTo(0L));
    }
}
