package blog.in.action.openfeign.simple;

import blog.in.action.annotation.InterfaceMeta;
import blog.in.action.client.SimpleClient;
import blog.in.action.repository.InterfaceHistory;
import blog.in.action.repository.InterfaceHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest
public class SimpleClientIT {

    @Autowired
    @Qualifier("testClient")
    SimpleClient simpleClient;

    @Autowired
    InterfaceHistoryRepository repository;

    LocalDateTime requestTime;
    LocalDateTime responseTime;

    @BeforeEach
    void setUp() {
        requestTime = LocalDateTime.now();
        responseTime = requestTime.plusSeconds(1);
    }

    @Test
    @Transactional
    void whenCallHomeMethod_thenExistsHistoryData() {
        MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class);
        Mockito.when(LocalDateTime.now())
                .thenReturn(requestTime, responseTime)
                .thenCallRealMethod();


        simpleClient.home();


        mockedStatic.close();
        InterfaceHistory entity = repository.findAll().get(0);
        assertThat(entity.getServiceId(), equalTo("0001"));
        assertThat(entity.getPath(), equalTo(new String[]{"/"}));
        assertThat(entity.getExplainText(), equalTo("블로그 홈"));
        assertThat(entity.getResponse(), equalTo("home"));
        assertThat(entity.getRequestTime(), equalTo(Timestamp.valueOf(requestTime)));
        assertThat(entity.getResponseTime(), equalTo(Timestamp.valueOf(responseTime)));
    }

    @Test
    @Transactional
    void whenCallAboutMethod_thenExistsHistoryData() {
        MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class);
        Mockito.when(LocalDateTime.now())
                .thenReturn(requestTime, responseTime)
                .thenCallRealMethod();


        simpleClient.about();


        mockedStatic.close();
        InterfaceHistory entity = repository.findAll().get(0);
        assertThat(entity.getServiceId(), equalTo("0002"));
        assertThat(entity.getPath(), equalTo(new String[]{"/about/"}));
        assertThat(entity.getExplainText(), equalTo("자기소개"));
        assertThat(entity.getResponse(), equalTo("about"));
        assertThat(entity.getRequestTime(), equalTo(Timestamp.valueOf(requestTime)));
        assertThat(entity.getResponseTime(), equalTo(Timestamp.valueOf(responseTime)));
    }
}

@Configuration
class TestConfig {

    @Bean(name = "testClient")
    public SimpleClient testClient() {
        return new SimpleClient() {
            
            @InterfaceMeta(explainText = "블로그 홈", serviceId = "0001")
            @GetMapping(path = "/")
            @Override
            public String home() {
                return "home";
            }

            @InterfaceMeta(explainText = "자기소개", serviceId = "0002")
            @GetMapping(path = "/about/")
            @Override
            public String about() {
                return "about";
            }
        };
    }
}