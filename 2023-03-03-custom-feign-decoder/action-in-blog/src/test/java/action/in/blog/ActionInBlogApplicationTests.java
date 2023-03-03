package action.in.blog;

import action.in.blog.client.BlogClient;
import action.in.blog.domain.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
    void contextLoads() {
        String expectResponse = "[{&#39;id&#39;: &#39;0001&#39;, &#39;title&#39;: &#39;hello world&#39;, &#39;content&#39;: &#39;this is post test for feign client&#39;}]";
        stubFor(get("/posts")
                .willReturn(
                        aResponse().withBody(expectResponse)
                ));


        List<Post> result = sut.getPosts();


        assertThat(result.size(), equalTo(1));
        Post post = result.get(0);
        assertThat(post.getId(), equalTo("0001"));
        assertThat(post.getTitle(), equalTo("hello world"));
        assertThat(post.getContent(), equalTo("this is post test for feign client"));
    }
}
