package blog.`in`.action

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinInActionApplication

fun main(args: Array<String>) {
    runApplication<KotlinInActionApplication>(*args)
}
