package action.in.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(properties = {"spring.profiles.active=value"})
class ValueApplicationTests {

    @Value("${redis.sentinels}")
    String[] sentinels;

    @Value("#{'${redis.sentinels}'.split(',')}")
    List<String> sentinelsWithSplitter;

    private String[] getHostAndPort(String sentinel) {
        int lastIndex = sentinel.lastIndexOf(":");
        return new String[]{sentinel.substring(0, lastIndex), sentinel.substring(lastIndex + 1)};
    }

    @Test
    void get_string_array_from_application_yml() {

        assertThat(sentinels.length, equalTo(3));
        String[] firstSentinel = getHostAndPort(sentinels[0]);
        assertThat(firstSentinel[0], equalTo("http://sentinel-1"));
        assertThat(firstSentinel[1], equalTo("8080"));
        String[] secondSentinel = getHostAndPort(sentinels[1]);
        assertThat(secondSentinel[0], equalTo("http://sentinel-2"));
        assertThat(secondSentinel[1], equalTo("8081"));
        String[] thirdSentinel = getHostAndPort(sentinels[2]);
        assertThat(thirdSentinel[0], equalTo("http://sentinel-3"));
        assertThat(thirdSentinel[1], equalTo("8082"));
    }

    @Test
    void get_string_list_from_application_yml() {

        assertThat(sentinelsWithSplitter.size(), equalTo(3));
        String[] firstSentinel = getHostAndPort(sentinelsWithSplitter.get(0));
        assertThat(firstSentinel[0], equalTo("http://sentinel-1"));
        assertThat(firstSentinel[1], equalTo("8080"));
        String[] secondSentinel = getHostAndPort(sentinelsWithSplitter.get(1));
        assertThat(secondSentinel[0], equalTo("http://sentinel-2"));
        assertThat(secondSentinel[1], equalTo("8081"));
        String[] thirdSentinel = getHostAndPort(sentinelsWithSplitter.get(2));
        assertThat(thirdSentinel[0], equalTo("http://sentinel-3"));
        assertThat(thirdSentinel[1], equalTo("8082"));
    }
}
