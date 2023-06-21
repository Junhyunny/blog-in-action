package action.in.blog.domain;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public
class Todo {
    private String title;
    @FormProperty("todo_ctnt")
    private String content;
}