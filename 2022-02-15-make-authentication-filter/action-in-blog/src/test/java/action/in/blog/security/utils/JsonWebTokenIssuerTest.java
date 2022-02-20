package action.in.blog.security.utils;

import action.in.blog.security.exception.JwtInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonWebTokenIssuerTest {

    JsonWebTokenIssuer jsonWebTokenIssuer;

    @BeforeEach
    public void setup() {
        jsonWebTokenIssuer = new JsonWebTokenIssuer(
                "secretKey",
                "refreshSecretKey",
                10,
                30);
    }

    Claims parseClaims(String jsonWebToken, String secretKey) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(jsonWebToken)
                .getBody();
    }

    @Test
    public void givenUser_whenCreateAccessTokenByUser_thenParsedClaimsWithSameValue() {

        String jwt = jsonWebTokenIssuer.createAccessToken("Junhyunny", "ROLE_ADMIN");

        Claims claims = parseClaims(jwt, "secretKey");

        assertThat(claims.getSubject(), equalTo("Junhyunny"));
        assertThat(claims.get("roles"), isA(List.class));
        List<String> roles = (List) claims.get("roles");
        for (String role : roles) {
            assertThat(role, equalTo("ROLE_ADMIN"));
        }
    }

    @Test
    public void givenUser_whenCreateRefreshTokenByUser_thenParsedClaimsWithSameValue() {

        String jwt = jsonWebTokenIssuer.createRefreshToken("Junhyunny", "ROLE_ADMIN");

        Claims claims = parseClaims(jwt, "refreshSecretKey");

        assertThat(claims.getSubject(), equalTo("Junhyunny"));
        assertThat(claims.get("roles"), isA(List.class));
        List<String> roles = (List) claims.get("roles");
        for (String role : roles) {
            assertThat(role, equalTo("ROLE_ADMIN"));
        }
    }

    @Test
    public void givenInValidRefreshToken_whenParseClaimsFromRefreshToken_thenThrowJwtInvalidException() {

        String invalidRefreshToken = "invalid refresh token";

        assertThrows(JwtInvalidException.class, () -> {
            jsonWebTokenIssuer.parseClaimsFromRefreshToken(invalidRefreshToken);
        });
    }

    @Test
    public void givenAccessToken_whenParseClaimsFromRefreshToken_thenThrowsJwtInvalidException() {

        String accessToken = jsonWebTokenIssuer.createAccessToken("Junhyunny", "ROLE_ADMIN");

        assertThrows(JwtInvalidException.class, () -> {
            jsonWebTokenIssuer.parseClaimsFromRefreshToken(accessToken);
        });
    }

    @Test
    public void givenRefreshToken_whenParseClaimsFromRefreshToken_thenReturnClaims() {

        String refreshToken = jsonWebTokenIssuer.createRefreshToken("Junhyunny", "ROLE_ADMIN");

        Claims claims = jsonWebTokenIssuer.parseClaimsFromRefreshToken(refreshToken);

        assertThat(claims.getSubject(), equalTo("Junhyunny"));
        assertThat(claims.get("roles"), isA(List.class));
        List<String> roles = (List) claims.get("roles");
        for (String role : roles) {
            assertThat(role, equalTo("ROLE_ADMIN"));
        }
    }
}
