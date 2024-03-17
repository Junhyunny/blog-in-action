package blog.`in`.action

import blog.`in`.action.domain.TodoEntity
import blog.`in`.action.repository.TodoRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest(
    properties = [
        "spring.jpa.hibernate.ddl-auto=create",
        "spring.jpa.show-sql=true"
    ]
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class SecondSetupTests {

    companion object {
        @Container
        @ServiceConnection
        val postgresContainer = PostgreSQLContainer<Nothing>("postgres:16")
    }

    @PersistenceContext
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var sut: TodoRepository

    @Test
    fun findByContentContainsIgnoreCase() {
        val entity = TodoEntity(content = "Hello World")
        entityManager.persist(entity)
        entityManager.flush()
        entityManager.clear()


        val result = sut.findByContentContainsIgnoreCase("Hello World")


        assertEquals(1, result.size)
        assertEquals("Hello World", result[0].content)
    }
}
