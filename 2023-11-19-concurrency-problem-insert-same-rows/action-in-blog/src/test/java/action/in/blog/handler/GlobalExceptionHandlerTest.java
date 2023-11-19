package action.in.blog.handler;

import action.in.blog.controller.CollectController;
import action.in.blog.exception.DuplicatedCollectException;
import action.in.blog.service.CollectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
        CollectController.class
})
class GlobalExceptionHandlerTest {

    @MockBean
    CollectService collectService;

    @Autowired
    MockMvc sut;

    @Test
    void handleDuplicatedCollectException() throws Exception {

        doThrow(new DuplicatedCollectException())
                .when(collectService)
                .collect(any(), any());


        sut.perform(post("/api/cards/A-01"))
                .andExpect(status().is(600))
                .andExpect(content().string("Already collected card"));
    }
}