package action.in.blog.todo.service;

import action.in.blog.todo.domain.Todo;
import action.in.blog.utils.RandomKeyGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

class TodoServiceTest {

    @Test
    void get_todo_after_adding_new_todo() {
        RandomKeyGenerator randomKeyGenerator = Mockito.mock(RandomKeyGenerator.class);
        when(randomKeyGenerator.getRandomKey()).thenReturn("1");
        Todo todo = new Todo();
        todo.setTitle("Hello World");


        TodoService sut = new DefaultTodoService(randomKeyGenerator);
        sut.addTodo(todo);


        List<Todo> todos = sut.getTodos();
        assertThat(todos.get(0).getId(), equalTo("1"));
        assertThat(todos.get(0).getTitle(), equalTo("Hello World"));
    }
}