package blog.`in`.action.repository

import blog.`in`.action.TestStoreConfig
import blog.`in`.action.domain.TodoEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class TodoRepositoryTest : TestStoreConfig() {

    @Autowired
    lateinit var sut: TodoRepository

    @Test
    fun findByContentContains() {
        val entity = TodoEntity(content = "This is new hello world")
        entityManager.persist(entity)
        flushAndClear()


        val result = sut.findByContentContains("hello world")


        assertEquals(1, result.size)
        assertEquals("This is new hello world", result[0].content)
    }
}