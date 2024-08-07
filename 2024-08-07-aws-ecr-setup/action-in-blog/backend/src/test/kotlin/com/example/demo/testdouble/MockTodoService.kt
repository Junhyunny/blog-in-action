package com.example.demo.testdouble

import com.example.demo.domain.Todo
import com.example.demo.service.TodoService

class MockTodoService : TodoService {

    var isCallGetTodos: Boolean = false
    lateinit var stubTodos: List<Todo>

    var isCallCreatTodo: Boolean = false
    lateinit var argCreateTodo: Todo

    fun setTodos(todos: List<Todo>) {
        stubTodos = todos
    }

    override fun getTodos(): List<Todo> {
        isCallGetTodos = true
        return stubTodos
    }

    override fun createTodo(todo: Todo) {
        isCallCreatTodo = true
        argCreateTodo = todo
    }
}
