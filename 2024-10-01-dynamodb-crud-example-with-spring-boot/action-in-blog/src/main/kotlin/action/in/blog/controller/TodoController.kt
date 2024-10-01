package action.`in`.blog.controller

import action.`in`.blog.controller.request.TodoRequest
import action.`in`.blog.controller.response.TodoResponse
import action.`in`.blog.repository.TodoRepository
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/todos")
class TodoController(val repository: TodoRepository) {

    @GetMapping
    fun getTodos(): List<TodoResponse> {
        return repository.getTodos()
            .map { TodoResponse.of(it) }
    }

    @GetMapping("/{id}")
    fun getTodo(@PathVariable id: String): TodoResponse {
        return TodoResponse.of(
            repository.getTodo(id)
        )
    }

    @PostMapping
    fun createTodo(@RequestBody requestBody: TodoRequest) {
        repository.createTodo(requestBody.toEntity())
    }

    @PutMapping("/{id}")
    fun updateTodo(
        @PathVariable id: String,
        @RequestBody requestBody: TodoRequest
    ) {
        repository.updateTodo(requestBody.toEntity(id))
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: String) {
        repository.deleteTodo(id)
    }
}