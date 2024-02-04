package blog.in.action.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;

class TodoServiceTest {

    TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService = spy(TodoService.class);
    }

    @Test
    void doReturn() {

        Mockito.doReturn(List.of("Stub"))
                .when(todoService)
                .getTodos();


        var result = todoService.getTodos();


        assertEquals(result, List.of("Stub"));
    }

    @Test
    void doReturnWithPrimitiveParameter() {

        Mockito.doReturn("Stub")
                .when(todoService)
                .getTodo(1);


        var result = todoService.getTodo(1);


        assertEquals("Stub", result);
    }

    @Test
    void doReturnWithWrongValuePrimitiveParameter() {

        Mockito.doReturn("Stub")
                .when(todoService)
                .getTodo(eq(2));


        var result = todoService.getTodo(1);


        assertEquals("Hello", result);
    }

    @Test
    void doReturnWithInstanceParameter() {

        var now = LocalDate.now();
        Mockito.doReturn(List.of("Stub"))
                .when(todoService)
                .getTodosInToday(now);


        var result = todoService.getTodosInToday(now);


        assertEquals(result, List.of("Stub"));
    }

    @Test
    void doReturnWithInstanceParameter_wrappedEq() {

        var now = LocalDate.now();
        Mockito.doReturn(List.of("Stub"))
                .when(todoService)
                .getTodosInToday(eq(now));


        var result = todoService.getTodosInToday(now);


        assertEquals(result, List.of("Stub"));
    }

    @Test
    void doReturnWithInstanceParameter_any() {

        var now = LocalDate.now();
        Mockito.doReturn(List.of("Stub"))
                .when(todoService)
                .getTodosInToday(any());


        var result = todoService.getTodosInToday(now);


        assertEquals(result, List.of("Stub"));
    }
}