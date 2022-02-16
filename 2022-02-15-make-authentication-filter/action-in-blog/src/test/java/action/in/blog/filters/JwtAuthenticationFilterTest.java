package action.in.blog.filters;

import action.in.blog.security.exception.JwtInvalidException;
import action.in.blog.security.tokens.JwtAuthenticationToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    MockHttpServletRequest mockRequest;
    MockHttpServletResponse mockResponse;
    FilterChain mockFilterChain;
    AuthenticationManager mockAuthenticationManager;

    JwtAuthenticationFilter filter;

    @BeforeEach
    public void setup() {
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        mockFilterChain = Mockito.mock(FilterChain.class);
        mockAuthenticationManager = Mockito.mock(AuthenticationManager.class);
        filter = new JwtAuthenticationFilter(mockAuthenticationManager);
    }

    @Test
    public void givenTokenNotInHeader_whenDoFilterInternal_thenAuthenticationManagerNotBeenCalled() throws ServletException, IOException {

        // setup
        when(mockAuthenticationManager.authenticate(any())).thenReturn(null);

        // action
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // verify
        verify(mockAuthenticationManager, never()).authenticate(any());
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }

    @Test
    public void givenInvalidTokenInHeader_whenDoFilterInternal_thenAuthenticationManagerNotBeenCalled() throws ServletException, IOException {

        // setup
        mockRequest.addHeader("Authorization", "invalid token");
        when(mockAuthenticationManager.authenticate(any())).thenReturn(null);

        // action
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // verify
        verify(mockAuthenticationManager, never()).authenticate(any());
        verify(mockFilterChain, times(1)).doFilter(mockRequest, mockResponse);
    }

    @Test
    public void givenReturnNullAfterAuthenticateWithValidToken_whenDoFilterInternal_thenAuthenticationFromSecurityContextHolderIsNull() throws ServletException, IOException {

        // setup
        mockRequest.addHeader("Authorization", "Bearer valid_token");
        JwtAuthenticationToken token = new JwtAuthenticationToken("valid_token");

        when(mockAuthenticationManager.authenticate(token)).thenReturn(null);

        // action
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // verify
        assertThat(SecurityContextHolder.getContext().getAuthentication(), nullValue());
    }

    @Test
    public void givenThrowAuthenticationException_whenDoFilterInternal_thenSecurityContextInContextHolderIsNullAndClearContextBeenCalled() throws ServletException, IOException {

        // setup
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        MockedStatic<SecurityContextHolder> utilities = Mockito.mockStatic(SecurityContextHolder.class);

        utilities.when(SecurityContextHolder::getContext).thenReturn(securityContext);

        mockRequest.addHeader("Authorization", "Bearer valid_token");
        JwtAuthenticationToken token = new JwtAuthenticationToken("valid_token");

        when(mockAuthenticationManager.authenticate(token)).thenThrow(new JwtInvalidException("time expired"));

        // action
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // verify
        utilities.verify(SecurityContextHolder::clearContext, times(1));
        assertThat(SecurityContextHolder.getContext().getAuthentication(), nullValue());

        // clear static Mockito
        Mockito.clearAllCaches();
    }

    @Test
    public void givenValidToken_whenDoFilterInternal_thenSecurityContextHasAuthentication() throws ServletException, IOException {

        mockRequest.addHeader("Authorization", "Bearer valid_token");
        JwtAuthenticationToken token = new JwtAuthenticationToken("valid_token");
        JwtAuthenticationToken authenticatedToken = new JwtAuthenticationToken(
                "Junhyunny",
                "",
                Collections.singletonList(
                        () -> "ROLE_ADMIN"
                )
        );

        when(mockAuthenticationManager.authenticate(token)).thenReturn(authenticatedToken);

        // action
        filter.doFilterInternal(mockRequest, mockResponse, mockFilterChain);

        // verify
        assertThat(SecurityContextHolder.getContext().getAuthentication(), equalTo(authenticatedToken));
    }

}
