package action.in.blog;

import action.in.blog.config.SentinelConfiguration;
import action.in.blog.config.SentinelConfiguration.SentinelInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(properties = {"spring.profiles.active=config"})
class ConfigurationPropertiesApplicationTests {

    @Autowired
    SentinelConfiguration sentinelConfiguration;

    @Test
    void get_sentinel_from_yml_using_bean() {

        List<SentinelInstance> sentinelInstances = sentinelConfiguration.getSentinels();


        assertThat(sentinelInstances.size(), equalTo(3));
        SentinelInstance firstInstance = sentinelInstances.get(0);
        assertThat(firstInstance.getHost(), equalTo("http://sentinel-1"));
        assertThat(firstInstance.getPort(), equalTo(8080));
        SentinelInstance secondInstance = sentinelInstances.get(1);
        assertThat(secondInstance.getHost(), equalTo("http://sentinel-2"));
        assertThat(secondInstance.getPort(), equalTo(8081));
        SentinelInstance thirdInstance = sentinelInstances.get(2);
        assertThat(thirdInstance.getHost(), equalTo("http://sentinel-3"));
        assertThat(thirdInstance.getPort(), equalTo(8082));
    }

}
