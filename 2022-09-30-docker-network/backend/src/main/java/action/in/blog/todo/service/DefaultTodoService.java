package action.in.blog.todo.service;

import action.in.blog.todo.Todo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class DefaultTodoService implements TodoService {

    private List<Todo> todos = new ArrayList<>();

    @Override
    public void addTodo(Todo todo) {
        synchronized (todos) {
            todos.add(todo);
        }
        log.info(todos);
    }

    @Override
    public List<Todo> getTodos() {
        return todos;
    }
}
