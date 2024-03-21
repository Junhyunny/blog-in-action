package blog.`in`.action.repository

import blog.`in`.action.TestStoreConfig
import blog.`in`.action.domain.ReplyEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class ReplyRepositoryTest : TestStoreConfig() {

    @Autowired
    lateinit var sut: ReplyRepository

    @Test
    fun findByContentContains() {
        val entity = ReplyEntity(content = "This is new hello world")
        entityManager.persist(entity)
        flushAndClear()


        val result = sut.findByContentContains("hello world")


        assertEquals(1, result.size)
        assertEquals("This is new hello world", result[0].content)
    }
}