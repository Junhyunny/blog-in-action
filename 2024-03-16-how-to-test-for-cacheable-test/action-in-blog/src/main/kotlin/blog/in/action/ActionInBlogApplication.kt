package blog.`in`.action

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class ActionInBlogApplication

fun main(args: Array<String>) {
    runApplication<ActionInBlogApplication>(*args)
}
