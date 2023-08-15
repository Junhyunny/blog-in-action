package action.`in`.blog.controller

import action.`in`.blog.domain.entity.UserEntity
import action.`in`.blog.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(
    properties = [
        "spring.datasource.url=jdbc:h2:mem:test",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa"
    ]
)
class RestControllerIT {

    private final val redis = GenericContainer("redis:5.0.3-alpine")
        .withExposedPorts(6379)

    init {
        redis.start()
        System.setProperty("spring.data.redis.host", redis.host)
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString())
    }

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var sut: MockMvc

    @BeforeEach
    fun setup() {
        userRepository.save(
            UserEntity(
                name = "Junhyunny",
                favoritePosts = mutableListOf(1L, 2L)
            )
        )
    }

    @Test
    fun saveUserEntityInSession() {

        sut.perform(get("/users/1"))
            .andExpect(status().isOk)
    }
}