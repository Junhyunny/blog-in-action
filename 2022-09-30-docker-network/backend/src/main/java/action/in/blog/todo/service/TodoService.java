package action.in.blog.todo.service;

import action.in.blog.todo.domain.Todo;

import java.util.List;

public interface TodoService {
    void addTodo(Todo todo);

    List<Todo> getTodos();
}
