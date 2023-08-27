package action.in.blog.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavoritePost {
    @Column(name = "post_id")
    private long postId;
    @Setter
    private String remark;
}
