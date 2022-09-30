package action.in.blog.todo.service;

import action.in.blog.todo.domain.Todo;
import action.in.blog.utils.RandomKeyGenerator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class DefaultTodoService implements TodoService {

    private List<Todo> todos = new ArrayList<>();

    private final RandomKeyGenerator randomKeyGenerator;

    public DefaultTodoService(RandomKeyGenerator randomKeyGenerator) {
        this.randomKeyGenerator = randomKeyGenerator;
    }

    @Override
    public void addTodo(Todo todo) {
        synchronized (todos) {
            todos.add(
                    Todo.builder()
                            .id(randomKeyGenerator.getRandomKey())
                            .title(todo.getTitle())
                            .build()
            );
        }
        log.info(todos);
    }

    @Override
    public List<Todo> getTodos() {
        return todos;
    }
}
