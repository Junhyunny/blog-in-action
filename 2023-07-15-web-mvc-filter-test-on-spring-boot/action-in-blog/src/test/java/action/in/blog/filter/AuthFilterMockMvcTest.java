package action.in.blog.filter;

import action.in.blog.provider.AuthProvider;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthFilterMockMvcTest {

    @Test
    void request_path_is_ignore_target_then_get_response() throws Exception {
        var authProvider = mock(AuthProvider.class);
        var sut = new AuthFilter(
                List.of("/foo"),
                authProvider
        );
        doThrow(new RuntimeException("client_id or client_secret is invalid"))
                .when(authProvider).authenticate();
        var mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthFilterTestController())
                .addFilter(sut)
                .build();


        mockMvc.perform(get("/foo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Hello Foo World"))
        ;
    }

    @Test
    void fail_authenticate_then_return_error_message() throws Exception {
        var authProvider = mock(AuthProvider.class);
        var sut = new AuthFilter(
                Collections.emptyList(),
                authProvider
        );
        doThrow(new RuntimeException("client_id or client_secret is invalid"))
                .when(authProvider).authenticate();
        var mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthFilterTestController())
                .addFilter(sut)
                .build();


        mockMvc.perform(get("/foo"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("client_id or client_secret is invalid"))
        ;
    }

    @Test
    void authenticate_successfully_then_get_response() throws Exception {
        var authProvider = mock(AuthProvider.class);
        var sut = new AuthFilter(
                Collections.emptyList(),
                authProvider
        );
        var mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthFilterTestController())
                .addFilter(sut)
                .build();


        mockMvc.perform(get("/foo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Hello Foo World"))
        ;
    }
}
