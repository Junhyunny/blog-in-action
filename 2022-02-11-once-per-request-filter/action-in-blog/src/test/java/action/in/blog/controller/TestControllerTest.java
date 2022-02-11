package action.in.blog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
public class TestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenCallTestEndPoint_thenRedirectToOtherEndPoint() throws Exception {

        mockMvc.perform(
                        get("/will-forward")
                )
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/forwarded"))
                .andReturn();
    }
}
