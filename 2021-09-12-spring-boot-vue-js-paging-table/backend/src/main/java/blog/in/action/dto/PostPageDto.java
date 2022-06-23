package blog.in.action.dto;

import blog.in.action.entity.Post;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPageDto {

    private List<PostDto> elements;

    private long totalElements;

    private int currentPage;

    private int totalPages;

    public static PostPageDto of(Page<Post> postPage) {
        return PostPageDto
            .builder()
            .elements(postPage.getContent().stream().map(entity -> PostDto.of(entity)).collect(Collectors.toList()))
            .totalElements(postPage.getTotalElements())
            .totalPages(postPage.getTotalPages())
            .currentPage(postPage.getNumber())
            .build();
    }
}
