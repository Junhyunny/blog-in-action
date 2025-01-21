package action.`in`.blog

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<ActionInBlogApplication>().with(TestcontainersConfiguration::class).run(*args)
}
