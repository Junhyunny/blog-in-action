package com.example.demo.service

import com.example.demo.domain.Todo
import com.example.demo.domain.TodoEntity
import com.example.demo.repository.TodoRepository
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import kotlin.test.assertEquals

@DataJpaTest
class DefaultTodoServiceIT {

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var todoRepository: TodoRepository

    lateinit var sut: TodoService

    @BeforeEach
    fun setUp() {
        sut = DefaultTodoService(todoRepository)
    }

    @Test
    fun `given data is existed in database when getTodos then return ordered by latest todo list`() {
        val firstTodo = TodoEntity(title = "Hello", createdAt = 1722320449081)
        val secondTodo = TodoEntity(title = "World", createdAt = 1722320509081)
        entityManager.persist(firstTodo)
        entityManager.persist(secondTodo)
        entityManager.flush()
        entityManager.clear()


        val result = sut.getTodos()


        assertEquals(
            Todo(id = secondTodo.id, title = "World", createdAt = "2024-07-30 15:21:49"),
            result[0]
        )
        assertEquals(
            Todo(id = firstTodo.id, title = "Hello", createdAt = "2024-07-30 15:20:49"),
            result[1]
        )
    }
}