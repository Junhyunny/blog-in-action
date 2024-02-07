package blog.`in`.action.repository

import blog.`in`.action.domain.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<TodoEntity, Long>