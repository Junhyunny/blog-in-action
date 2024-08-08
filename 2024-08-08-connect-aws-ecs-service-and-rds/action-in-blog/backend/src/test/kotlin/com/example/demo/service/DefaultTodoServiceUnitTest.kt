package com.example.demo.service

import com.example.demo.domain.Todo
import com.example.demo.domain.TodoEntity
import com.example.demo.repository.TodoRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.kotlin.argumentCaptor
import kotlin.test.assertEquals

class DefaultTodoServiceUnitTest {

    lateinit var mockTodoRepository: TodoRepository
    lateinit var sut: TodoService

    @BeforeEach
    fun setUp() {
        mockTodoRepository = Mockito.mock(TodoRepository::class.java)
        sut = DefaultTodoService(mockTodoRepository)
    }

    @Test
    fun `given data is existed when getTodos then return todo list`() {
        Mockito.`when`(mockTodoRepository.findAllByOrderByCreatedAtDesc())
            .thenReturn(
                listOf(
                    TodoEntity(id = 1, title = "Hello", createdAt = 1722320449081),
                    TodoEntity(id = 2, title = "World", createdAt = 1722320509081),
                )
            )


        val result = sut.getTodos()


        assertEquals(
            Todo(id = 1, title = "Hello", createdAt = "2024-07-30 15:20:49"),
            result[0]
        )
        assertEquals(
            Todo(id = 2, title = "World", createdAt = "2024-07-30 15:21:49"),
            result[1]
        )
    }

    @Test
    fun `when createTodo then repository save function is called`() {
        sut.createTodo(
            Todo(id = 0, title = "Hello", createdAt = "2024-07-30 15:21:49")
        )


        val argumentCaptor = argumentCaptor<TodoEntity>()
        Mockito.verify(mockTodoRepository, times(1))
            .save(argumentCaptor.capture())
        val result = argumentCaptor.firstValue
        assertEquals(0, result.id)
        assertEquals("Hello", result.title)
        assertEquals(1722320509000, result.createdAt)
    }
}