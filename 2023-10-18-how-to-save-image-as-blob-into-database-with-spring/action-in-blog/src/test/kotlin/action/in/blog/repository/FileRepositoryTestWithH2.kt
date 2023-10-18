package action.`in`.blog.repository

import action.`in`.blog.domain.FileEntity
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.mock.web.MockMultipartFile
import java.util.*

@DataJpaTest(
    properties = [
        "spring.datasource.url=jdbc:h2:mem:test;MODE=PostgreSQL",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.show-sql=true",
        "spring.jpa.generate-ddl=true"
    ]
)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FileRepositoryTestWithH2 {

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
        assertEquals(fileName, result.name)
        assertEquals("image/png", result.contentType)
        assertArrayEquals("some-image-binary".toByteArray(), result.binaryData)
    }
}