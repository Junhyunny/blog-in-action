package blog.`in`.action.controller

import blog.`in`.action.domain.Todo
import blog.`in`.action.repository.TodoRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoController(private val todoRepository: TodoRepository) {

    @GetMapping("/todos")
    fun todos(): List<Todo> = todoRepository.findAll().map { Todo.of(it) }.toList()
}