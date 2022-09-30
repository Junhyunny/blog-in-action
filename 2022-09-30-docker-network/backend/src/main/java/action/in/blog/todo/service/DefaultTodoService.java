package action.in.blog.todo.service;

import action.in.blog.todo.Todo;

import java.util.ArrayList;
import java.util.List;

public class DefaultTodoService implements TodoService {

    private List<Todo> todos = new ArrayList<>();

    @Override
    public void addTodo(Todo todo) {
        synchronized (todos) {
            todos.add(todo);
        }
    }

    @Override
    public List<Todo> getTodos() {
        return todos;
    }
}
