package blog.in.action.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SimpleDeferredResultControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new SimpleDeferredResultController()).build();
    }

    @Test
    public void whenGetData_thenStatusIsOk() throws Exception {
        mockMvc.perform(get("/data"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted());
    }

    @Test
    public void whenGetData_thenGetMessage() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/data"))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("HelloWorld"));
    }
}
