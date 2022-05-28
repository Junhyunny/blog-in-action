package blog.in.action.openfeign.simple;

import blog.in.action.annotation.InterfaceMeta;
import blog.in.action.repository.InterfaceHistory;
import blog.in.action.repository.InterfaceHistoryRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsEqual.equalTo;

@FeignClient(name = "simple-client", url = "https://junhyunny.github.io")
interface SimpleClient {

    @InterfaceMeta(explainText = "블로그 홈", serviceId = "0001")
    @GetMapping(path = "/")
    String home();

    @InterfaceMeta(explainText = "자기소개", serviceId = "0002")
    @GetMapping(path = "/about/")
    String about();
}

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