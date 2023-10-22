package action.`in`.blog.controller

import action.`in`.blog.domain.FileEntity
import action.`in`.blog.repository.FileRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class FileControllerTest {

    lateinit var fileRepository: FileRepository
    lateinit var sut: MockMvc

    @BeforeEach
    fun setUp() {
        fileRepository = mock(FileRepository::class.java)
        sut = MockMvcBuilders.standaloneSetup(
            FileController("http://localhost:8080", fileRepository)
        ).build()
    }

    @Test
    fun getImage() {

        `when`(fileRepository.findById(1))
            .thenReturn(
                Optional.of(
                    FileEntity(1L, "image/jpeg", "fileName", "binaryData".toByteArray())
                )
            )


        sut.perform(
            get("/api/files/1/images/fileName")
        )
            .andExpect(status().isOk)
            .andExpect(header().string("CACHE-CONTROL", "max-age=2592000"))
            .andExpect(content().contentType("image/jpeg"))
            .andExpect(content().bytes("binaryData".toByteArray()))
    }
}