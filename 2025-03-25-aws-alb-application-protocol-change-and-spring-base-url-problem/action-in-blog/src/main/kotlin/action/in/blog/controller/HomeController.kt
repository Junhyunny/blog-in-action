package action.`in`.blog.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/health")
    fun index() = "ok"

    @GetMapping("/home")
    fun home() = "Hello World"
}