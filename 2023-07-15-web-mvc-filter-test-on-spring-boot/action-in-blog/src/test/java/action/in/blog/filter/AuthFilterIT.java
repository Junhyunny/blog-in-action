package action.in.blog.filter;

import action.in.blog.provider.AuthProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = {AuthFilterTestController.class},
        properties = {"spring.profiles.active=auth-filter", "auth.ignore-paths=/bar"}
)
public class AuthFilterIT {

    @MockBean
    AuthProvider authProvider;
    @Autowired
    MockMvc mockMvc;

    @Test
    void request_path_is_ignore_target_then_get_response() throws Exception {

        doThrow(new RuntimeException("client_id or client_secret is invalid"))
                .when(authProvider).authenticate();


        mockMvc.perform(get("/bar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Hello Bar World"))
        ;
    }

    @Test
    void fail_authenticate_then_return_error_message() throws Exception {

        doThrow(new RuntimeException("client_id or client_secret is invalid"))
                .when(authProvider).authenticate();


        mockMvc.perform(get("/foo"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("client_id or client_secret is invalid"))
        ;
    }

    @Test
    void authenticate_successfully_then_get_response() throws Exception {

        mockMvc.perform(get("/foo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Hello Foo World"))
        ;
    }
}
