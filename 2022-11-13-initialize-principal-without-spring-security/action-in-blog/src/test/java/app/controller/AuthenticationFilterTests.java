package app.controller;

import app.filter.AuthenticationFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationFilterTests {

    @Test
    @DisplayName("AuthenticationFilter 를 통과하면 사용자 정보를 획득할 수 있다.")
    void get_user_information_through_authentication_filter() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new ResourceController())
                .addFilter(new AuthenticationFilter())
                .build();


        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("0001"))
                .andExpect(jsonPath("$.name").value("Junhyunny"))
                .andExpect(jsonPath("$.roles[0]").value("ADMIN"))
                .andExpect(jsonPath("$.roles[1]").value("USER"))
                .andExpect(jsonPath("$.roles[2]").value("MANAGER"))
        ;
    }
}