package action.`in`.blog.repository

import action.`in`.blog.domain.FileEntity
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.mock.web.MockMultipartFile
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.*

@DataJpaTest(
    properties = [
        "spring.datasource.url=jdbc:tc:postgresql:latest:///test",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
        "spring.jpa.show-sql=true",
        "spring.jpa.generate-ddl=true"
    ]
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class FileRepositoryTestWithPostgres {

    @Container
    var postgresContainer = PostgreSQLContainer("postgres:latest").withDatabaseName("test")

    @Autowired
    lateinit var entityManager: EntityManager

    @Autowired
    lateinit var sut: FileRepository

    fun flushAndClear() {
        entityManager.flush()
        entityManager.clear()
    }

    @Test
    fun insert() {

        val fileName = UUID.randomUUID().toString()
        val mockFile = MockMultipartFile(
            "file",
            "profile.png",
            "image/png",
            "some-image-binary".toByteArray()
        )
        val entity = FileEntity(
            name = fileName,
            contentType = mockFile.contentType ?: "image/jpeg",
            binaryData = mockFile.bytes
        )


        sut.save(entity)


        flushAndClear()
        val result = entityManager.find(FileEntity::class.java, entity.id)
        Assertions.assertEquals(fileName, result.name)
        Assertions.assertEquals("image/png", result.contentType)
        Assertions.assertArrayEquals("some-image-binary".toByteArray(), result.binaryData)
    }
}