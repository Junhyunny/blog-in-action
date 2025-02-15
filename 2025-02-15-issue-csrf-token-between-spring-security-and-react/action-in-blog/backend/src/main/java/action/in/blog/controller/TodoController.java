package action.in.blog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

record Todo(
        String content
) {
}

@RestController
public class TodoController {

    @PostMapping("/api/todos")
    void createTodos(@RequestBody Todo todo) {
        System.out.println(todo);
    }
}
