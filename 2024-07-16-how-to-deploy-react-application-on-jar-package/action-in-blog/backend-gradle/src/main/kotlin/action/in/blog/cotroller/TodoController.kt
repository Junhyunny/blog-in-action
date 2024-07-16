package action.`in`.blog.backend.cotroller

import action.`in`.blog.backend.domain.Todo
import action.`in`.blog.backend.repository.TodoRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoRepository: TodoRepository
) {

    @GetMapping
    fun getTodos(): MutableList<Todo> = todoRepository.findAll()
        .map { Todo.from(it) }
        .toMutableList()

    @PostMapping
    fun createTodo(@RequestBody todo: Todo) = todoRepository.save(todo.toEntity())
}