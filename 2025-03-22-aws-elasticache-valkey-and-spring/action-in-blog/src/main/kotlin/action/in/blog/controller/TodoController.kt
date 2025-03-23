package action.`in`.blog.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

data class Todo(
    val title: String,
    val description: String
) {
    constructor(): this("", "")
}

@RestController
class TodoController {

    @GetMapping("/todos")
    fun todos(servletRequest: HttpServletRequest): Todo {
        val session = servletRequest.session
        val todo = session.getAttribute("TODO") as Todo?
        if (todo != null) {
            return todo
        }
        val result = Todo("Hello World", "This is session test")
        session.setAttribute("TODO", result)
        return result
    }
}