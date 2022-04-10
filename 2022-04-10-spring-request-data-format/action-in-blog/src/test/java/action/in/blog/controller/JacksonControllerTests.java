package action.in.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class JacksonControllerTests {

    @Test
    void givenStringDateFormat_whenGetJacksonDto_thenReturnJacksonResponse() throws Exception {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("date", "2022-04-10 10:25:00.000");
        requestBody.put("timestamp", "2022-04-10 10:25:00.000");
        requestBody.put("localDateTime", "2022-04-10 10:25:00.000");

        ObjectMapper objectMapper = new ObjectMapper();

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new JacksonController()).build();

        mockMvc.perform(
                        post("/jackson")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody))
                )
                .andExpect(jsonPath("$.date", equalTo("2022-04-10 10:25:00.000")))
                .andExpect(jsonPath("$.timestamp", equalTo("2022-04-10 10:25:00.000")))
                .andExpect(jsonPath("$.localDateTime", equalTo("2022-04-10 10:25:00.000")));
    }
}
