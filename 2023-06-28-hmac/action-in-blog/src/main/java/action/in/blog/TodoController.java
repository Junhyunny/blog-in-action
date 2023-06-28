package action.in.blog;

import lombok.Builder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

record Todo(
        long id,
        String content
) {
    @Builder
    public Todo {
    }
}

@RestController
class TodoController {

    @GetMapping("/todos")
    public List<Todo> getPosts() {
        return Arrays.asList(
                new Todo(1000, "Hello"),
                new Todo(1001, "World"),
                new Todo(1002, "Study"),
                new Todo(1003, "Java")
        );
    }

    @DeleteMapping("/todos/{id}")
    public long deleteTodo(@PathVariable long id) {
        return id;
    }

    @PostMapping("/todos")
    public Todo createTodo(@RequestBody Todo todo) {
        return Todo.builder()
                .id(2000)
                .content(todo.content())
                .build();
    }

    @PutMapping("/todos")
    public Todo updateTodo(Todo todo) {
        return Todo.builder()
                .id(todo.id())
                .content(todo.content())
                .build();
    }
}