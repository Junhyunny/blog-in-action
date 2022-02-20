package action.in.blog.security.service;

import action.in.blog.security.dto.JsonWebTokenDto;
import action.in.blog.security.dto.UserDto;
import action.in.blog.security.entity.User;
import action.in.blog.security.exception.JwtInvalidException;
import action.in.blog.security.repository.AuthRepository;
import action.in.blog.security.utils.JsonWebTokenIssuer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    AuthRepository mockAuthRepository;
    PasswordEncoder passwordEncoder;
    JsonWebTokenIssuer mockJwtIssuer;

    AuthService authService;

    @BeforeEach
    public void setup() {
        mockAuthRepository = Mockito.mock(AuthRepository.class);
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        mockJwtIssuer = Mockito.mock(JsonWebTokenIssuer.class);
        authService = new AuthService(mockAuthRepository, passwordEncoder, mockJwtIssuer);
    }

    UserDto getUserDto(String userName, String password) {
        return UserDto.builder()
                .userName(userName)
                .password(password)
                .build();
    }

    User getUser(String userName, String password, String authority) {
        return User.builder()
                .userName(userName)
                .password(passwordEncoder.encode(password))
                .authority(authority)
                .build();
    }

    @Test
    public void givenNotExistUserName_whenLogin_thenThrowUsernameNotFoundException() {

        UserDto userDto = getUserDto("Junhyunny", "1234");

        Throwable throwable = assertThrows(UsernameNotFoundException.class, () -> {
            authService.login(userDto);
        });

        assertThat(throwable, isA(UsernameNotFoundException.class));
        assertThat(throwable.getMessage(), equalTo("username is not found"));
    }

    @Test
    public void givenNotMatchedPassword_whenLogin_thenThrowBadCredentialsException() {

        UserDto userDto = getUserDto("Junhyunny", "1234");
        when(mockAuthRepository.findById("Junhyunny")).thenReturn(
                Optional.of(
                        getUser("Junhyunny", "12345", "ROLE_ADMIN")
                )
        );

        Throwable throwable = assertThrows(BadCredentialsException.class, () -> {
            authService.login(userDto);
        });

        assertThat(throwable, isA(BadCredentialsException.class));
        assertThat(throwable.getMessage(), equalTo("bad credential: using unmatched password"));
    }

    @Test
    public void givenValidUserDto_whenLogin_thenReturnJsonWebTokenDto() {

        UserDto userDto = getUserDto("Junhyunny", "1234");
        User user = getUser("Junhyunny", "1234", "ROLE_ADMIN");
        when(mockAuthRepository.findById("Junhyunny")).thenReturn(Optional.of(user));
        when(mockJwtIssuer.createAccessToken("Junhyunny", "ROLE_ADMIN")).thenReturn("accessToken");
        when(mockJwtIssuer.createRefreshToken("Junhyunny", "ROLE_ADMIN")).thenReturn("refreshToken");

        JsonWebTokenDto jsonWebTokenDto = authService.login(userDto);

        assertThat(jsonWebTokenDto.getGrantType(), equalTo("Bearer"));
        assertThat(jsonWebTokenDto.getAccessToken(), equalTo("accessToken"));
        assertThat(jsonWebTokenDto.getRefreshToken(), equalTo("refreshToken"));
    }

    @Test
    public void givenInvalidGrandType_whenReissue_thenThrowJwtInvalidException() {

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            authService.reissue("refreshToken");
        });
        assertThat(throwable.getMessage(), equalTo("invalid grant type"));
    }

    @Test
    public void givenNullClaims_whenReissue_thenThrowJwtInvalidException() {

        when(mockJwtIssuer.parseClaimsFromRefreshToken("refreshToken")).thenReturn(null);

        Throwable throwable = assertThrows(JwtInvalidException.class, () -> {
            authService.reissue("Bearer refreshToken");
        });
        assertThat(throwable.getMessage(), equalTo("not exists claims in token"));
    }

    @Test
    public void givenValidRefreshToken_whenReissue_thenJsonWebTokenDto() {

        User user = getUser("Junhyunny", "1234", "ROLE_ADMIN");
        Claims claims = Jwts.claims().setSubject("Junhyunny");
        claims.put("roles", Collections.singleton("ROLE_ADMIN"));

        when(mockAuthRepository.findById("Junhyunny")).thenReturn(Optional.of(user));
        when(mockJwtIssuer.parseClaimsFromRefreshToken("refreshToken")).thenReturn(claims);
        when(mockJwtIssuer.createAccessToken("Junhyunny", "ROLE_ADMIN")).thenReturn("accessToken");
        when(mockJwtIssuer.createRefreshToken("Junhyunny", "ROLE_ADMIN")).thenReturn("refreshToken");

        JsonWebTokenDto jsonWebTokenDto = authService.reissue("Bearer refreshToken");

        assertThat(jsonWebTokenDto.getGrantType(), equalTo("Bearer"));
        assertThat(jsonWebTokenDto.getAccessToken(), equalTo("accessToken"));
        assertThat(jsonWebTokenDto.getRefreshToken(), equalTo("refreshToken"));
    }
}
