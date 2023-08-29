package action.in.blog.domain;

public record Todo(
        long userId,
        long id,
        String title,
        boolean completed
) {
}
