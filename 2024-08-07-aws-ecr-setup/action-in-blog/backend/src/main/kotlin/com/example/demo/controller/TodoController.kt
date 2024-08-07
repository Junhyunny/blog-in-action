package com.example.demo.controller

import com.example.demo.domain.Todo
import com.example.demo.service.TodoService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class TodoController(
    private val todoService: TodoService,
) {

    @GetMapping("/todos")
    fun getTodos(): List<Todo> {
        return todoService.getTodos()
    }

    @PostMapping("/todos")
    fun addTodo(
        @RequestBody todo: Todo
    ) {
        todoService.createTodo(todo)
    }
}
