package action.in.blog.todo.controller;

import action.in.blog.todo.Todo;
import action.in.blog.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TodoControllerTest {

    @Test
    void receive_new_todo_and_delegate_command_to_service() throws Exception {
        TodoService mockTodoService = Mockito.mock(TodoService.class);
        MockMvc sut = MockMvcBuilders.standaloneSetup(new TodoController(mockTodoService)).build();

        ObjectMapper objectMapper = new ObjectMapper();
        Todo todo = new Todo();
        todo.setTitle("Hello World");


        sut.perform(
                post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo))
        ).andExpect(status().isOk());


        ArgumentCaptor<Todo> argumentCaptor = ArgumentCaptor.forClass(Todo.class);
        verify(mockTodoService, times(1)).addTodo(argumentCaptor.capture());
        Todo capturedTodo = argumentCaptor.getValue();
        assertThat(capturedTodo.getTitle(), equalTo("Hello World"));
    }
}