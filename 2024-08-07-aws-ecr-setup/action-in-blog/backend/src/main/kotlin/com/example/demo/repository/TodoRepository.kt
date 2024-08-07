package com.example.demo.repository

import com.example.demo.domain.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<TodoEntity, Long> {
    fun findAllByOrderByCreatedAtDesc(): List<TodoEntity>
}
