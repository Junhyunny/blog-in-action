package action.in.blog;

import blog.in.action.annotation.CustomAutoConfiguration;
import blog.in.action.beans.FirstBean;
import blog.in.action.beans.SecondBean;
import blog.in.action.beans.ThirdBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@CustomAutoConfiguration
@ExtendWith({SpringExtension.class})
public class ImportAutoConfigurationTests {

    @Autowired
    FirstBean firstBean;

    @Autowired
    SecondBean secondBean;

    @Autowired
    ThirdBean thirdBean;

    @Test
    public void whenRunTestWithSpringExtension_thenAllBeansNotNull() {

        assertThat(firstBean, notNullValue());
        assertThat(secondBean, notNullValue());
        assertThat(thirdBean, notNullValue());
    }
}
