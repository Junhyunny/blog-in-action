package action.in.blog.todo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {

    private String title;

    @Override
    public String toString() {
        return "{title: " + title + "}";
    }
}
