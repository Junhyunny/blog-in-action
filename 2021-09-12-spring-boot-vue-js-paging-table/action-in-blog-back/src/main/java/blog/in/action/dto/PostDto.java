package blog.in.action.dto;

import blog.in.action.entity.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;

    private String title;

    private String contents;

    private LocalDateTime createdAt;

    public static PostDto of(Post post) {
        return PostDto
            .builder()
            .id(post.getId())
            .title(post.getTitle())
            .contents(post.getContents())
            .createdAt(post.getCreatedAt())
            .build();
    }
}
