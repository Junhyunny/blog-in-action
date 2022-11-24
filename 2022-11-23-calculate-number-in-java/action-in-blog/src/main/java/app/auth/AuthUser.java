package app.auth;

import lombok.*;

import java.security.Principal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
@ToString
public class AuthUser implements Principal {

    private String id;
    private String name;
    private String returnUrl;
}
