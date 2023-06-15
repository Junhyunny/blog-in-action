package action.in.blog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Author {

    protected long id;
    protected String penName;
    protected String name;
    protected String email;
    protected String contact;
    protected String sex;

    public static Author of(action.in.blog.wsdl.Author authorResponse) {
        return Author.builder()
                .id(authorResponse.getId())
                .penName(authorResponse.getPenName())
                .name(authorResponse.getName())
                .email(authorResponse.getEmail())
                .contact(authorResponse.getContact())
                .sex(authorResponse.getSex().value())
                .build();
    }
}
