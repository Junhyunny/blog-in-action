package blog.`in`.action.integration

import blog.`in`.action.TestContainerDatabase
import blog.`in`.action.repository.TodoRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TodoIntegrationTest : TestContainerDatabase() {

    @Autowired
    lateinit var sut: TodoRepository

    @Test
    fun findByContentContains() {

        val result = sut.findByContentContains("hello world")


        Assertions.assertEquals(0, result.size)
    }
}