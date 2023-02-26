package action.in.blog;

import action.in.blog.client.BlogClient;
import action.in.blog.domain.BlogQuery;
import action.in.blog.domain.BlogResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import feign.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.cloud.contract.spec.internal.MediaTypes.APPLICATION_JSON;

@TestConfiguration
class FeignLoggingConfig {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

@Import({FeignLoggingConfig.class})
@SpringBootTest
@AutoConfigureWireMock(port = 0)
@TestPropertySource(
        properties = {
                "blog-server.url=http://localhost:${wiremock.server.port}",
                "logging.level.action.in.blog.client=DEBUG"
        })
public class ActionInBlogApplicationTests {

    @Autowired
    BlogClient sut;

    @Test
    void query_params_with_params() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<BlogResponse> blogResponses = Arrays.asList(
                BlogResponse.builder()
                        .name("junny")
                        .age(21)
                        .address("Seoul")
                        .build(),
                BlogResponse.builder()
                        .name("junhyunny")
                        .age(22)
                        .address("Seoul")
                        .build()
        );
        String expectedResult = objectMapper.writeValueAsString(blogResponses);
        stubFor(get(urlPathEqualTo("/search"))
                .withQueryParams(
                        Map.of(
                                "name", WireMock.equalTo("jun"),
                                "age", WireMock.equalTo("20"),
                                "address", WireMock.equalTo("Seoul")
                        )
                ).willReturn(
                        aResponse().withStatus(200)
                                .withHeader("Content-Type", APPLICATION_JSON)
                                .withBody(expectedResult)
                )
        );


        List<BlogResponse> result = sut.getBlogResponsesWithParams(20, "jun", "Seoul");


        assertThat(result.size(), equalTo(2));
        BlogResponse firstItem = result.get(0);
        assertThat(firstItem.getName(), equalTo("junny"));
        assertThat(firstItem.getAge(), equalTo(21));
        assertThat(firstItem.getAddress(), equalTo("Seoul"));
        BlogResponse secondItem = result.get(1);
        assertThat(secondItem.getName(), equalTo("junhyunny"));
        assertThat(secondItem.getAge(), equalTo(22));
        assertThat(secondItem.getAddress(), equalTo("Seoul"));
    }

    @Test
    void query_params_with_dto() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<BlogResponse> blogResponses = Arrays.asList(
                BlogResponse.builder()
                        .name("junny")
                        .age(21)
                        .address("Seoul")
                        .build(),
                BlogResponse.builder()
                        .name("junhyunny")
                        .age(22)
                        .address("Seoul")
                        .build()
        );
        String expectedResult = objectMapper.writeValueAsString(blogResponses);
        stubFor(get(urlPathEqualTo("/search"))
                .withQueryParams(
                        Map.of(
                                "name", WireMock.equalTo("jun"),
                                "age", WireMock.equalTo("20"),
                                "address", WireMock.equalTo("Seoul")
                        )
                ).willReturn(
                        aResponse().withStatus(200)
                                .withHeader("Content-Type", APPLICATION_JSON)
                                .withBody(expectedResult)
                )
        );


        List<BlogResponse> result = sut.getBlogResponsesWithDto(
                BlogQuery.builder()
                        .name("jun")
                        .age(20)
                        .address("Seoul")
                        .build()
        );


        assertThat(result.size(), equalTo(2));
        BlogResponse firstItem = result.get(0);
        assertThat(firstItem.getName(), equalTo("junny"));
        assertThat(firstItem.getAge(), equalTo(21));
        assertThat(firstItem.getAddress(), equalTo("Seoul"));
        BlogResponse secondItem = result.get(1);
        assertThat(secondItem.getName(), equalTo("junhyunny"));
        assertThat(secondItem.getAge(), equalTo(22));
        assertThat(secondItem.getAddress(), equalTo("Seoul"));
    }
}
