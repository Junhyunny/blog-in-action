package action.in.blog.todo.controller;

import action.in.blog.todo.domain.Todo;
import action.in.blog.todo.service.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/todos")
    public List<Todo> getTodos() {
        return todoService.getTodos();
    }
}
