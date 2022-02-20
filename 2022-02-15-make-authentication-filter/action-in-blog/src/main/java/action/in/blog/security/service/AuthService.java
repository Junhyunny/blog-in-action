package action.in.blog.security.service;

import action.in.blog.security.dto.JsonWebTokenDto;
import action.in.blog.security.dto.UserDto;
import action.in.blog.security.entity.User;
import action.in.blog.security.exception.JwtInvalidException;
import action.in.blog.security.repository.AuthRepository;
import action.in.blog.security.utils.JsonWebTokenIssuer;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AuthService {

    private final String GRANT_TYPE_BEARER = "Bearer";

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebTokenIssuer jwtIssuer;

    public AuthService(
            AuthRepository authRepository,
            PasswordEncoder passwordEncoder,
            JsonWebTokenIssuer jwtIssuer) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtIssuer = jwtIssuer;
    }

    private String resolveToken(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(GRANT_TYPE_BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private JsonWebTokenDto createJsonWebTokenDto(User user) {
        String userName = user.getUserName();
        String authority = user.getAuthority();
        return JsonWebTokenDto.builder()
                .grantType(GRANT_TYPE_BEARER)
                .accessToken(jwtIssuer.createAccessToken(userName, authority))
                .refreshToken(jwtIssuer.createRefreshToken(userName, authority))
                .build();
    }

    public JsonWebTokenDto login(UserDto userDto) {

        User user = authRepository.findById(userDto.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("username is not found"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("bad credential: using unmatched password");
        }

        return createJsonWebTokenDto(user);
    }

    public JsonWebTokenDto reissue(String bearerToken) {

        String refreshToken = resolveToken(bearerToken);
        if (!StringUtils.hasText(refreshToken)) {
            throw new JwtInvalidException("invalid grant type");
        }

        Claims claims = jwtIssuer.parseClaimsFromRefreshToken(refreshToken);
        if (claims == null) {
            throw new JwtInvalidException("not exists claims in token");
        }

        User user = authRepository.findById(claims.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("username is not found"));

        return createJsonWebTokenDto(user);
    }
}
