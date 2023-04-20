package action.in.blog.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.security.Principal;
import java.util.List;

@Getter
@Builder
@ToString
public class AuthenticatedUser implements Principal {

    private String id;
    private String name;
    private List<String> roles;

    @Override
    public String getName() {
        return name;
    }
}
