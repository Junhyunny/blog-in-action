package action.`in`.blog.controller

import action.`in`.blog.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    private val sessionKeyUser: String = "SESSION_KEY_USER"

    @GetMapping("/users/{id}")
    fun users(
        servletRequest: HttpServletRequest,
        @PathVariable("id") id: Long
    ) {
        val user = userService.getUser(id)
        val session = servletRequest.getSession(true)
        session.setAttribute(sessionKeyUser, user)
    }
}