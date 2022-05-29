package blog.in.action.openfeign.simple;

import blog.in.action.client.SimpleClient;
import blog.in.action.repository.InterfaceHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest
public class SimpleClientTest {

    @Autowired
    private SimpleClient simpleClient;

    @Autowired
    private InterfaceHistoryRepository repository;

    @Test
    @Transactional
    public void whenCallHomeMethod_thenExistsHistoryData() {

        simpleClient.home();

        assertThat(repository.count(), greaterThan(0L));
    }

    @Test
    @Transactional
    public void whenCallAboutMethod_thenExistsHistoryData() {

        simpleClient.about();

        assertThat(repository.count(), greaterThan(0L));
    }
}