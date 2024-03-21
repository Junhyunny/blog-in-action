package blog.`in`.action

import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.annotation.DirtiesContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@DirtiesContext
@Testcontainers
abstract class TestContainerDatabase {

    companion object {

        @Container
        @ServiceConnection
        val postgresContainer = PostgreSQLContainer("postgres:16")

//        init {
//            postgresContainer.start()
//        }
//
//        @JvmStatic
//        @DynamicPropertySource
//        fun setPostgresProperties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
//            registry.add("spring.datasource.username", postgresContainer::getUsername)
//            registry.add("spring.datasource.password", postgresContainer::getPassword)
//        }
    }
}