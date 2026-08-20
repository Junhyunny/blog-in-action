package action.in.blog.actioninblog.todo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @Transactional(readOnly = true)
    void streamAllReturnsEveryTodo() {
        try (Stream<TodoEntity> todos = todoRepository.streamAll()) {
            todos.forEach(todo -> System.out.println(todo.getTitle()));
        }
    }
}
