package app.auth;

import lombok.Builder;
import lombok.Getter;

import java.security.Principal;
import java.util.List;

@Getter
@Builder
public class AuthenticatedUser implements Principal {

    private String id;
    private String name;
    private List<String> roles;

    @Override
    public String getName() {
        return name;
    }
}
