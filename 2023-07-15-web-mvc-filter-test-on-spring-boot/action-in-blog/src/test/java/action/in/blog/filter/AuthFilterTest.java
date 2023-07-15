package action.in.blog.filter;

import action.in.blog.provider.AuthProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthFilterTest {

    @Test
    void request_path_is_ignore_target_then_do_next_filter() throws ServletException, IOException {
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var filterChain = mock(FilterChain.class);
        var authProvider = mock(AuthProvider.class);
        var sut = new AuthFilter(
                List.of("/bar"),
                authProvider
        );
        request.setRequestURI("/bar");


        sut.doFilterInternal(request, response, filterChain);


        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void fail_authenticate_then_return_error_message() throws ServletException, IOException {
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var filterChain = mock(FilterChain.class);
        var authProvider = mock(AuthProvider.class);
        var sut = new AuthFilter(
                Collections.emptyList(),
                authProvider
        );
        doThrow(new RuntimeException("client_id or client_secret is invalid"))
                .when(authProvider).authenticate();


        sut.doFilterInternal(request, response, filterChain);


        assertEquals(401, response.getStatus());
        assertEquals("client_id or client_secret is invalid", response.getContentAsString());
    }

    @Test
    void authenticate_successfully_then_do_next_filter() throws ServletException, IOException {
        var request = new MockHttpServletRequest();
        var response = new MockHttpServletResponse();
        var filterChain = mock(FilterChain.class);
        var authProvider = mock(AuthProvider.class);
        var sut = new AuthFilter(
                Collections.emptyList(),
                authProvider
        );


        sut.doFilterInternal(request, response, filterChain);


        verify(filterChain, times(1)).doFilter(request, response);
    }
}
