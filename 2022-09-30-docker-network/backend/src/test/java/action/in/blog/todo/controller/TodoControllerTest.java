package action.in.blog.todo.controller;

import action.in.blog.todo.domain.Todo;
import action.in.blog.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TodoControllerTest {

    TodoService mockTodoService;
    MockMvc sut;

    @BeforeEach
    void setUp() {
        mockTodoService = Mockito.mock(TodoService.class);
        sut = MockMvcBuilders.standaloneSetup(new TodoController(mockTodoService)).build();
    }

    @Test
    void receive_new_todo_and_delegate_command_to_service() throws Exception {

        ArgumentCaptor<Todo> argumentCaptor = ArgumentCaptor.forClass(Todo.class);
        ObjectMapper objectMapper = new ObjectMapper();
        Todo todo = new Todo();
        todo.setTitle("Hello World");


        sut.perform(
                post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo))
        ).andExpect(status().isOk());


        verify(mockTodoService, times(1)).addTodo(argumentCaptor.capture());
        Todo capturedTodo = argumentCaptor.getValue();
        assertThat(capturedTodo.getTitle(), equalTo("Hello World"));
    }

    @Test
    void respond_todos() throws Exception {
        Todo todo = new Todo();
        todo.setId("1");
        todo.setTitle("Hello World");
        when(mockTodoService.getTodos()).thenReturn(Arrays.asList(todo));


        sut.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo("1")))
                .andExpect(jsonPath("$[0].title", equalTo("Hello World")))
        ;
    }
}