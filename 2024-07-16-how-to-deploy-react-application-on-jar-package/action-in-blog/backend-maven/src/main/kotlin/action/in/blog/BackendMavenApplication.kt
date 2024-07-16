package action.`in`.blog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendMavenApplication

fun main(args: Array<String>) {
    runApplication<BackendMavenApplication>(*args)
}
