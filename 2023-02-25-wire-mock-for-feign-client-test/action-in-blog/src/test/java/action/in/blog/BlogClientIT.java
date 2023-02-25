package action.in.blog;

import action.in.blog.client.BlogClient;
import action.in.blog.domain.BlogResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.cloud.contract.spec.internal.MediaTypes.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@TestPropertySource(
        properties = {
                "blog-server.url=http://localhost:${wiremock.server.port}"
        }
)
public class BlogClientIT {

    @Autowired
    BlogClient sut;

    @Test
    void get_blog_response_from_wiremock_server_using_feign() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String dateTime = "2023-02-24 11:30:25";
        LocalDateTime now = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String expectedResponse = objectMapper.writeValueAsString(
                Map.of(
                        "message", "Hello World",
                        "createdAt", dateTime
                )
        );
        stubFor(get(urlPathEqualTo("/search"))
                .withQueryParam("key", WireMock.equalTo("hello"))
                .willReturn(
                        aResponse().withStatus(200)
                                .withHeader("Content-Type", APPLICATION_JSON)
                                .withBody(expectedResponse)
                ));


        BlogResponse result = sut.getBlogResponse("hello");


        assertThat(result.getMessage(), equalTo("Hello World"));
        assertThat(result.getCreatedAt(), equalTo(now));
    }
}
