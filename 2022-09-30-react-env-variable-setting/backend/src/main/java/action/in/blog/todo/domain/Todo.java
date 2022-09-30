package action.in.blog.todo.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {

    private String id;
    private String title;

    @Override
    public String toString() {
        return String.format("{id: %s, title: %s}", id, title);
    }
}
