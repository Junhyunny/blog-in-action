package com.example.demo.controller

import com.example.demo.domain.Todo
import com.example.demo.service.TodoService
import com.example.demo.util.TimeProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class TodoControllerTest {

    lateinit var mockTodoService: TodoService
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        mockTodoService = mock(TodoService::class.java)
        mockMvc = MockMvcBuilders
            .standaloneSetup(
                TodoController(mockTodoService)
            )
            .build()
    }

    @Test
    fun `when request todo list then 200(status is OK)`() {
        mockMvc.get("/api/todos")
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    fun `given data is existed when request todo list then return todo list`() {
        `when`(mockTodoService.getTodos())
            .thenReturn(
                listOf(
                    Todo(id = 1, title = "hello"),
                    Todo(id = 2, title = "world"),
                )
            )


        mockMvc.get("/api/todos")
            .andExpect {
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].title") { value("hello") }
                jsonPath("$[1].id") { value(2) }
                jsonPath("$[1].title") { value("world") }
            }
        verify(mockTodoService, times(1))
            .getTodos()
    }

    @Test
    fun `when request todo creation then 200(status is OK)`() {
        val mockedTimeProvider = mockStatic(TimeProvider::class.java)
        mockedTimeProvider.`when`<Long> { TimeProvider.now() }.thenReturn(1722320449081)
        mockedTimeProvider.`when`<String> { TimeProvider.toStringFormat(1722320449081) }.thenReturn("2024-07-30 15:20:49")


        mockMvc.post("/api/todos") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                    "id": 0,
                    "title": "Hello World"
                }
            """.trimIndent()
        }.andExpect {
            status { isOk() }
        }
        verify(mockTodoService, times(1))
            .createTodo(
                Todo(id = 0, title = "Hello World", createdAt = "2024-07-30 15:20:49")
            )
        mockedTimeProvider.close()
    }
}
