package action.in.blog;

import action.in.blog.client.TodoClient;
import action.in.blog.domain.Todo;
import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import feign.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import wiremock.org.eclipse.jetty.util.MultiMap;
import wiremock.org.eclipse.jetty.util.UrlEncoded;

import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Configuration
class FeignLoggingConfig {

    @Bean
    public Logger.Level getLoggerLevel() {
        return Logger.Level.FULL;
    }
}


@AutoConfigureWireMock(port = 0)
@SpringBootTest(
        properties = {
                "todo-service.url=http://localhost:${wiremock.server.port}",
                "logging.level.action.in.blog=DEBUG"
        }
)
public class FormDataFeignTest {

    @Autowired
    TodoClient todoClient;

    @Test
    void createTodo() {

        var requestBody = new MultiMap<String>();
        requestBody.put("todo_ctnt", "This is a new todo");
        requestBody.put("title", "Hello World");
        stubFor(post("/todo")
                .withRequestBody(
                        new ContainsPattern(
                                UrlEncoded.encode(
                                        requestBody,
                                        StandardCharsets.UTF_8,
                                        true
                                )
                        )
                )
                .willReturn(
                        aResponse().withStatus(200)
                                .withHeader(
                                        "Content-Type", MediaType.APPLICATION_JSON_VALUE
                                )
                                .withBody("1000")
                )
        );


        var result = todoClient.createTodo(
                Todo.builder()
                        .title("Hello World")
                        .content("This is a new todo")
                        .build()
        );


        assertThat(result, equalTo(1000L));
    }
}
