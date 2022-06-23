package blog.in.action.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockAsyncContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.AsyncListener;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DeferredResultControllerTest {

    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DeferredResultController()).build();
    }

    @Test
    public void givenUserNameParams_whenRequestAuthentication_thenIsOk() throws Exception {

        mockMvc.perform(get("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted());
    }

    @Test
    public void givenUserNameParams_whenRequestAuthentication_thenPoolSizeOne() throws Exception {

        mockMvc.perform(get("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted());

        mockMvc.perform(get("/pool-size"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void givenUserNameParams_whenAuthenticate_thenIsOk() throws Exception {

        mockMvc.perform(post("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenRequestAuthenticationAuthenticate_whenAsyncDispatch_thenReturnTrue() throws Exception {

        // given
        MvcResult result = mockMvc.perform(get("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(post("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(status().isOk());

        // when, then
        mockMvc.perform(asyncDispatch(result))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void givenRequestAuthenticationAuthenticate_whenAsyncDispatch_thenPoolSizeZero() throws Exception {

        // given
        mockMvc.perform(get("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(status().isOk())
                .andExpect(request().asyncStarted())
                .andReturn();
        mockMvc.perform(post("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(status().isOk());

        // when, then
        mockMvc.perform(get("/pool-size"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void givenRequestAuthentication_whenTimeout_thenIs5xxServerError() throws Exception {

        // given
        MvcResult result = mockMvc.perform(get("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(request().asyncStarted())
                .andReturn();

        // when
//        MockAsyncContext ctx = (MockAsyncContext) result.getRequest().getAsyncContext();
//        for (AsyncListener listener : ctx.getListeners()) {
//            listener.onTimeout(null);
//        }
//
//        // then
//        mockMvc.perform(asyncDispatch(result))
//                .andExpect(status().is5xxServerError());

        // given
        result.getRequest().getAsyncContext().setTimeout(10);

        // when, then
        assertThrows(RuntimeException.class, () -> {
            asyncDispatch(result);
            // result.getAsyncResult(); // 동일한 결과
        });
    }

    @Test
    public void givenRequestAuthentication_whenTimeout_thenPoolSizeZero() throws Exception {

        // given
        MvcResult result = mockMvc.perform(get("/authentication")
                        .param("userName", "Junhyunny"))
                .andExpect(request().asyncStarted())
                .andReturn();

        // when
        MockAsyncContext ctx = (MockAsyncContext) result.getRequest().getAsyncContext();
        for (AsyncListener listener : ctx.getListeners()) {
            listener.onTimeout(null);
        }

        // then
        mockMvc.perform(get("/pool-size"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}