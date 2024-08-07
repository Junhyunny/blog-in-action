package com.example.demo.service

import com.example.demo.domain.Todo
import com.example.demo.domain.TodoEntity
import com.example.demo.repository.TodoRepository
import com.example.demo.util.TimeProvider.parseStringToTimestamp
import com.example.demo.util.TimeProvider.toStringFormat
import org.springframework.stereotype.Service

interface TodoService {
    fun getTodos(): List<Todo>
    fun createTodo(todo: Todo)
}

@Service
class DefaultTodoService(
    private val todoRepository: TodoRepository
) : TodoService {
    override fun getTodos(): List<Todo> {
        return todoRepository.findAllByOrderByCreatedAtDesc()
            .map {
                Todo(
                    id = it.id,
                    title = it.title,
                    createdAt = toStringFormat(it.createdAt)
                )
            }
    }

    override fun createTodo(todo: Todo) {
        todoRepository.save(
            TodoEntity(
                title = todo.title,
                createdAt = parseStringToTimestamp(todo.createdAt)
            )
        )
    }
}