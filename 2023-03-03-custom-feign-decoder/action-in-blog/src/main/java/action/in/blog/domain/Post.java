package action.in.blog.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    private String id;
    private String title;
    private String content;
}
