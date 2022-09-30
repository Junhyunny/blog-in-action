package action.in.blog.todo.service;

import action.in.blog.todo.Todo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TodoServiceTest {

    @Test
    void get_todo_after_adding_new_todo() {

        Todo todo = new Todo();
        todo.setTitle("Hello World");


        TodoService sut = new DefaultTodoService();
        sut.addTodo(todo);


        List<Todo> todos = sut.getTodos();
        assertThat(todos.get(0).getTitle(), equalTo("Hello World"));
    }
}