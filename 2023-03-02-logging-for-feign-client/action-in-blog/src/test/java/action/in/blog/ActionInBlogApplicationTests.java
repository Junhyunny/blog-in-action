package action.in.blog;

import action.in.blog.client.BlogClient;
import action.in.blog.domain.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.cloud.contract.spec.internal.MediaTypes.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureWireMock(port = 0)
@TestPropertySource(
        properties = {
                "server-url=http://localhost:${wiremock.server.port}"
        }
)
class ActionInBlogApplicationTests {

    @Autowired
    BlogClient sut;

    @Test
    void get_method() {
        stubFor(get(urlPathEqualTo("/health"))
                .willReturn(
                        aResponse().withStatus(200).withBody("OK")
                ));


        String result = sut.health();


        assertThat(result, equalTo("OK"));
    }

    @Test
    void get_method_with_request_param() {
        stubFor(get(urlPathEqualTo("/search"))
                .withQueryParam("keyword", WireMock.equalTo("hello"))
                .willReturn(
                        aResponse().withStatus(200).withBody("OK")
                ));


        String result = sut.search("hello");


        assertThat(result, equalTo("OK"));
    }

    @Test
    void post_method_with_request_body() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> request = new HashMap<>();
        request.put("id", null);
        request.putAll(Map.of(
                "title", "hello world",
                "content", "this is post test for feign client"
        ));
        String requestJson = objectMapper.writeValueAsString(request);
        String expectedResponse = objectMapper.writeValueAsString(
                Map.of(
                        "id", "0001",
                        "title", "hello world",
                        "content", "this is post test for feign client"
                )
        );
        stubFor(post(urlPathEqualTo("/post"))
                .withHeader("Content-Type", WireMock.equalTo(APPLICATION_JSON))
                .withRequestBody(equalToJson(requestJson))
                .willReturn(
                        aResponse().withStatus(200)
                                .withHeader("Content-Type", APPLICATION_JSON)
                                .withBody(expectedResponse)
                ));


        Post result = sut.createPost(
                Post.builder()
                        .title("hello world")
                        .content("this is post test for feign client")
                        .build()
        );


        assertThat(result.getId(), equalTo("0001"));
        assertThat(result.getTitle(), equalTo("hello world"));
        assertThat(result.getContent(), equalTo("this is post test for feign client"));
    }
}
