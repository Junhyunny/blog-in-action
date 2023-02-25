package action.in.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
class RestTemplateBuilderTest {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void get_something_from_server_using_rest_template() throws JsonProcessingException {
        String uri = "http://blog-in-action.com/search?key={key}";
        String dateTime = "2023-02-24 11:30:25";
        LocalDateTime now = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String expectedResponse = objectMapper.writeValueAsString(
                Map.of(
                        "message", "Hello World",
                        "createdAt", dateTime
                )
        );
        RestTemplate sut = restTemplateBuilder.build();
        MockRestServiceServer server = MockRestServiceServer.bindTo(sut).build();
        server.expect(requestToUriTemplate(uri, "hello"))
                .andRespond(withSuccess(expectedResponse, MediaType.APPLICATION_JSON));


        BlogResponse result = sut.getForObject(uri, BlogResponse.class, "hello");


        assertThat(result.getMessage(), equalTo("Hello World"));
        assertThat(result.getCreatedAt(), equalTo(now));
    }
}
