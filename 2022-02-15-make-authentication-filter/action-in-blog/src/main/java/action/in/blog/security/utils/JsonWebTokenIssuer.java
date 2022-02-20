package action.in.blog.security.utils;

import action.in.blog.security.exception.JwtInvalidException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class JsonWebTokenIssuer {

    private final int ONE_SECONDS = 1000;
    private final int ONE_MINUTE = 60 * ONE_SECONDS;
    private final String KEY_ROLES = "roles";

    private final byte[] secretKeyBytes;
    private final byte[] refreshSecretKeyBytes;
    private final int expireMin;
    private final int refreshExpireMin;

    public JsonWebTokenIssuer(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.refresh-secret}") String refreshSecretKey,
            @Value("${jwt.expire-min:10}") int expireMin,
            @Value("${jwt.refresh-expire-min:30}") int refreshExpireMin) {
        this.secretKeyBytes = secretKey.getBytes();
        this.refreshSecretKeyBytes = refreshSecretKey.getBytes();
        this.expireMin = expireMin;
        this.refreshExpireMin = refreshExpireMin;
    }

    private String createToken(String userName, String authority, byte[] secretKeyBytes, int expireMin) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put(KEY_ROLES, Collections.singleton(authority));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ONE_MINUTE * expireMin))
                .signWith(SignatureAlgorithm.HS256, secretKeyBytes)
                .compact();
    }

    public String createAccessToken(String userName, String authority) {
        return createToken(userName, authority, secretKeyBytes, expireMin);
    }

    public String createRefreshToken(String userName, String authority) {
        return createToken(userName, authority, refreshSecretKeyBytes, refreshExpireMin);
    }

    public Claims parseClaimsFromRefreshToken(String jsonWebToken) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(refreshSecretKeyBytes).parseClaimsJws(jsonWebToken).getBody();
        } catch (SignatureException signatureException) {
            throw new JwtInvalidException("signature key is different", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtInvalidException("expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtInvalidException("malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtInvalidException("using illegal argument like null", illegalArgumentException);
        }
        return claims;
    }
}
