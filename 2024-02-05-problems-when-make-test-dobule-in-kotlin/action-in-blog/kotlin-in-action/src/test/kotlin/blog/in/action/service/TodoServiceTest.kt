package blog.`in`.action

import blog.`in`.action.service.TodoService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito
import org.mockito.Mockito.spy
import java.time.LocalDate

class TodoServiceTest {

    lateinit var todoService: TodoService

    @BeforeEach
    fun setUp() {
        todoService = spy(TodoService::class.java)
    }

    @Test
    fun doReturn() {

        Mockito.doReturn(listOf("Stub"))
            .`when`(todoService)
            .getTodos()


        val result = todoService.getTodos()


        assertEquals(result, listOf("Stub"))
    }

    @Test
    fun doReturnWithPrimitiveParameter() {

        Mockito.doReturn("Stub")
            .`when`(todoService)
            .getTodo(1L)


        val result = todoService.getTodo(1L)


        assertEquals("Stub", result)
    }

    @Test
    fun doReturnWithWrongValuePrimitiveParameter() {

        Mockito.doReturn("Stub")
            .`when`(todoService)
            .getTodo(2L)


        val result = todoService.getTodo(1L)


        assertEquals("Hello", result)
    }

    @Test
    fun doReturnWithPrimitiveParameter_anyLong() {

        Mockito.doReturn("Stub")
            .`when`(todoService)
            .getTodo(anyLong())


        val result = todoService.getTodo(1L)


        assertEquals("Stub", result)
    }

    @Test
    fun doReturnWithInstanceParameter() {

        val now = LocalDate.now()
        Mockito.doReturn(listOf("Stub"))
            .`when`(todoService)
            .getTodosInToday(now)


        val result = todoService.getTodosInToday(now)


        assertEquals(result, listOf("Stub"))
    }

    @Test
    fun doReturnWithInstanceParameter_wrappedEq() {

        val now = LocalDate.now()
        Mockito.doReturn(listOf("Stub"))
            .`when`(todoService)
            .getTodosInToday(eq(now))


        val result = todoService.getTodosInToday(now)


        assertEquals(result, listOf("Stub"))
    }

    @Test
    fun doReturnWithInstanceParameter_any() {

        val now = LocalDate.now()
        Mockito.doReturn(listOf("Stub"))
            .`when`(todoService)
            .getTodosInToday(any())


        val result = todoService.getTodosInToday(now)


        assertEquals(result, listOf("Stub"))
    }

    @Test
    fun doReturnWithInstanceTwoParameters() {

        val now = LocalDate.now()
        Mockito.doReturn(listOf("Stub"))
            .`when`(todoService)
            .getFilteredTodosInToday(anyList(), eq(now))


        val result = todoService.getFilteredTodosInToday(listOf(1L), now)


        assertEquals(result, listOf("Stub"))
    }
}