package blog.in.action;

import blog.in.action.controller.PostController;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ActionInBlogTest {

    MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(PostController.class)
                .build();
    }

    @Test
    void withoutJsonIgnoreProperties_throwException() {

        var throwable = assertThrows(Exception.class, () -> mockMvc.perform(get("/posts")));


        var cause = throwable.getCause();
        assertInstanceOf(HttpMessageNotWritableException.class, throwable.getCause());
        assertInstanceOf(JsonMappingException.class, cause.getCause());
        cause.printStackTrace();
    }

    @Test
    void withJsonIgnoreProperties_isOk() throws Exception {

        mockMvc.perform(get("/posts"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].content").value("Hello World"))
                .andExpect(jsonPath("$[0].replies[0].id").value(1L))
                .andExpect(jsonPath("$[0].replies[0].content").value("This is reply"))
                .andExpect(jsonPath("$[0].replies[0].post.id").value(1L))
                .andExpect(jsonPath("$[0].replies[0].post.content").value("Hello World"))
                .andExpect(jsonPath("$[0].replies[0].post.replies").doesNotExist())
                .andDo(print())
        ;
    }
}
