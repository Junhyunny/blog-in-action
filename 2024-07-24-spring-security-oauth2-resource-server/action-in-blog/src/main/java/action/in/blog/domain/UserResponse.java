package action.in.blog.domain;

public record UserResponse(
        int id,
        String name,
        String accessToken
) {
}
