package action.in.blog.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DateTimeFormatControllerTests {

    @Test
    void givenStringDateFormat_whenRequestParam_thenReturnJacksonResponse() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new DateTimeFormatController()).build();

        mockMvc.perform(
                        get("/request-param")
                                .param("date", "2022-04-10 10:25:00.000")
                                .param("localDateTime", "2022-04-10 10:25:00.000")
                )
                .andExpect(jsonPath("$.date", equalTo("2022-04-10 10:25:00.000")))
                .andExpect(jsonPath("$.localDateTime", equalTo("2022-04-10 10:25:00.000")));
    }

    @Test
    void givenStringDateFormat_whenModelAttribute_thenReturnJacksonResponse() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new DateTimeFormatController()).build();

        mockMvc.perform(
                        post("/model-attribute")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("date", "2022-04-10 10:25:00.000")
                                .param("localDateTime", "2022-04-10 10:25:00.000")
                )
                .andExpect(jsonPath("$.date", equalTo("2022-04-10 10:25:00.000")))
                .andExpect(jsonPath("$.localDateTime", equalTo("2022-04-10 10:25:00.000")));
    }
}
