package action.in.blog.todo.controller;

import action.in.blog.todo.Todo;
import action.in.blog.todo.service.TodoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public void addTodo(@RequestBody Todo todo) {
        todoService.addTodo(todo);
    }
}
