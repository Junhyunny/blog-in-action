package blog.`in`.action

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ActionInBlogApplication

fun main(args: Array<String>) {
    // System.setProperty("user.timezone", "Asia/Seoul")
    runApplication<ActionInBlogApplication>(*args)
}
