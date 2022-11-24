package app.auth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.*;

class SessionFilterTests {

    String redirectUrl = "http://my-server/sso/auth";

    @Test
    @DisplayName("요청 정보에서 세션을 얻지 못하면 다음 인증 URL 으로 리다이렉트한다.")
    public void fail_to_acquire_session_from_request_then_redirect_to_auth_url() throws IOException, ServletException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = new MockFilterChain();

        when(request.getSession(false)).thenReturn(null);


        OncePerRequestFilter sut = new SessionFilter(redirectUrl);
        sut.doFilter(request, response, filterChain);


        verify(response, times(1)).sendRedirect(redirectUrl);
    }

    @Test
    @DisplayName("요청 정보에서 세션을 획득하면 다음 필터를 수행한다.")
    public void success_to_acquire_session_from_request_then_do_next_filter() throws ServletException, IOException {

        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain filterChain = mock(FilterChain.class);

        request.setSession(session);


        OncePerRequestFilter sut = new SessionFilter(redirectUrl);
        sut.doFilter(request, response, filterChain);


        verify(filterChain, times(1)).doFilter(request, response);
    }
}