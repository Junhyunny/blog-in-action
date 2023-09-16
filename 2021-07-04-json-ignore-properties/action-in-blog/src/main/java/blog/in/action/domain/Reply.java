package blog.in.action.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

public record Reply(
        long id,
        String content,
        @JsonIgnoreProperties(value = "replies")
        Post post
) {
    @Builder
    public Reply {
    }
}
