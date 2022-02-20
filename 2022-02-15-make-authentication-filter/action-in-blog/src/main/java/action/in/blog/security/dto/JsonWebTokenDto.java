package action.in.blog.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JsonWebTokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
