package blog.`in`.action.integration

import blog.`in`.action.TestContainerDatabase
import blog.`in`.action.repository.ReplyRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReplyIntegrationTest : TestContainerDatabase() {

    @Autowired
    lateinit var sut: ReplyRepository

    @Test
    fun findByContentContains() {

        val result = sut.findByContentContains("hello world")


        Assertions.assertEquals(0, result.size)
    }
}