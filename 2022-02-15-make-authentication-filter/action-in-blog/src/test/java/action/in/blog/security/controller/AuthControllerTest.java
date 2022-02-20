package action.in.blog.security.controller;

import action.in.blog.security.dto.JsonWebTokenDto;
import action.in.blog.security.utils.JsonWebTokenIssuer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(value = {
        "jwt.secret=accessSecretKey",
        "jwt.refresh-secret=refreshSecretKey",
        "jwt.expire-min=10",
        "jwt.refresh-expire-min=30"
})
@AutoConfigureMockMvc
public class AuthControllerTest {

    final int ONE_SECONDS = 1000;
    final int ONE_MINUTE = 60 * ONE_SECONDS;
    final String KEY_ROLES = "roles";

    @Autowired
    MockMvc mockMvc;

    @SpyBean
    JsonWebTokenIssuer spyJsonWebTokenIssuer;

    @AfterEach
    public void clear() {
        Mockito.reset(spyJsonWebTokenIssuer);
    }

    private String createToken(String userName, List<String> roles, Date now, int expireMin, String secretKey) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put(KEY_ROLES, roles);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ONE_MINUTE * expireMin))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    private String getAccessToken() {
        return createToken(
                "Junhyunny",
                Collections.singletonList("ROLE_ADMIN"),
                new Date(),
                10,
                "accessSecretKey");
    }

    private String getRefreshToken() {
        return createToken(
                "Junhyunny",
                Collections.singletonList("ROLE_ADMIN"),
                new Date(),
                30,
                "refreshSecretKey");
    }

    @Test
    public void givenWithoutToken_whenCallLogin_thenIsOk() throws Exception {

        mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("userName", "Junhyunny")
                                .param("password", "123")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void givenWithoutToken_whenCallNotExistsPath_thenIsForbidden() throws Exception {

        mockMvc.perform(
                        post("/something-other")
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenInvalidToken_whenCallNotExistsPath_thenIsForbidden() throws Exception {

        String inValidToken = createToken(
                "Junhyunny",
                Collections.singletonList("ROLE_ADMIN"),
                new Date(),
                30,
                "inValidAccessSecretKey");

        mockMvc.perform(
                        post("/something-other").
                                header("Authorization", "Bearer " + inValidToken)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenExpiredToken_whenCallNotExistsPath_thenIsForbidden() throws Exception {

        Date past = new Date(System.currentTimeMillis() - ONE_MINUTE * 10);
        String expiredToken = createToken(
                "Junhyunny",
                Collections.singletonList("ROLE_ADMIN"),
                past,
                5,
                "accessSecretKey");

        mockMvc.perform(
                        post("/something-other").
                                header("Authorization", "Bearer " + expiredToken)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenValidToken_whenCallNotExistsPath_thenNotFound() throws Exception {

        String accessToken = getAccessToken();

        mockMvc.perform(
                        post("/something-other").
                                header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNotExistsUserDto_whenLogin_thenIsForbidden() throws Exception {

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userName", "Not-Junhyunny")
                        .param("password", "123")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void givenNotMatchedPasswordDto_whenLogin_thenIsForbidden() throws Exception {

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userName", "Junhyunny")
                        .param("password", "1234")
        ).andExpect(status().isForbidden());
    }

    @Test
    public void givenValidUserDto_whenLogin_thenReturnAccessToken() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String accessToken = getAccessToken();
        String refreshToken = getRefreshToken();
        when(spyJsonWebTokenIssuer.createAccessToken("Junhyunny", "ROLE_ADMIN")).thenReturn(accessToken);
        when(spyJsonWebTokenIssuer.createRefreshToken("Junhyunny", "ROLE_ADMIN")).thenReturn(refreshToken);

        MvcResult mvcResult = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("userName", "Junhyunny")
                                .param("password", "123")
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonWebTokenDto jsonWebTokenDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), JsonWebTokenDto.class);
        assertThat(jsonWebTokenDto.getAccessToken(), equalTo(accessToken));
        assertThat(jsonWebTokenDto.getRefreshToken(), equalTo(refreshToken));
        assertThat(jsonWebTokenDto.getGrantType(), equalTo("Bearer"));
    }

    @Test
    public void givenWithoutAuthorization_whenReissue_thenIsBadRequest() throws Exception {

        mockMvc.perform(
                        post("/auth/reissue")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenNotBearerToken_whenReissue_thenIsForbidden() throws Exception {

        String refreshToken = getRefreshToken();

        mockMvc.perform(
                        post("/auth/reissue")
                                .header("Authorization", refreshToken)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenAccessToken_whenReissue_thenIsForbidden() throws Exception {

        String accessToken = getAccessToken();

        mockMvc.perform(
                        post("/auth/reissue")
                                .header("Authorization", "Bearer " + accessToken)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenRefreshToken_whenReissue_thenReturnAccessToken() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        String accessToken = getAccessToken();
        String refreshToken = getRefreshToken();
        when(spyJsonWebTokenIssuer.createAccessToken("Junhyunny", "ROLE_ADMIN")).thenReturn(accessToken);
        when(spyJsonWebTokenIssuer.createRefreshToken("Junhyunny", "ROLE_ADMIN")).thenReturn(refreshToken);

        MvcResult mvcResult = mockMvc.perform(
                        post("/auth/reissue")
                                .header("Authorization", "Bearer " + refreshToken)
                )
                .andExpect(status().isOk())
                .andReturn();

        JsonWebTokenDto jsonWebTokenDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), JsonWebTokenDto.class);
        assertThat(jsonWebTokenDto.getAccessToken(), equalTo(accessToken));
        assertThat(jsonWebTokenDto.getRefreshToken(), equalTo(refreshToken));
        assertThat(jsonWebTokenDto.getGrantType(), equalTo("Bearer"));
    }
}
