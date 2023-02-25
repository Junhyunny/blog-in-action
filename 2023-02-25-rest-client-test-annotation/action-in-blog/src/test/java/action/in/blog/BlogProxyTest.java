package action.in.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@Service
class BlogProxy {

    private final String baseURI = "http://blog-in-action.com";

    private final RestTemplate restTemplate;

    BlogProxy(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public BlogResponse getBlogResponse(String key) {
        return restTemplate.getForObject(baseURI.concat("/search?key={key}"), BlogResponse.class, key);
    }
}

@RestClientTest(BlogProxy.class)
public class BlogProxyTest {

    @Autowired
    BlogProxy sut;
    @Autowired
    MockRestServiceServer server;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void get_something_from_server_using_rest_template() throws JsonProcessingException {
        String dateTime = "2023-02-24 11:30:25";
        LocalDateTime now = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String expectedResponse = objectMapper.writeValueAsString(
                Map.of(
                        "message", "Hello World",
                        "createdAt", dateTime
                )
        );
        server.expect(requestToUriTemplate("http://blog-in-action.com/search?key={key}", "hello"))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));


        BlogResponse result = sut.getBlogResponse("hello");


        assertThat(result.getMessage(), equalTo("Hello World"));
        assertThat(result.getCreatedAt(), equalTo(now));
    }
}
