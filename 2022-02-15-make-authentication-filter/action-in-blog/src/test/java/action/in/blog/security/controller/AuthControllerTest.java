package action.in.blog.security.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(value = {
        "jwt.secret=validSecretKey"
})
@AutoConfigureMockMvc
public class AuthControllerTest {

    final int ONE_SECONDS = 1000;
    final int ONE_MINUTE = 60 * ONE_SECONDS;
    final String KEY_ROLES = "roles";

    @Autowired
    MockMvc mockMvc;

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

    @Test
    public void givenWithoutToken_whenCallLogin_thenIsOk() throws Exception {

        mockMvc.perform(
                        post("/auth/login")
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
                "invalidSecreteKey");

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
                "validSecretKey");

        mockMvc.perform(
                        post("/something-other").
                                header("Authorization", "Bearer " + expiredToken)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void givenValidToken_whenCallNotExistsPath_thenNotFound() throws Exception {

        String validToken = createToken(
                "Junhyunny",
                Collections.singletonList("ROLE_ADMIN"),
                new Date(),
                30,
                "validSecretKey");

        mockMvc.perform(
                        post("/something-other").
                                header("Authorization", "Bearer " + validToken)
                )
                .andExpect(status().isNotFound());
    }
}
