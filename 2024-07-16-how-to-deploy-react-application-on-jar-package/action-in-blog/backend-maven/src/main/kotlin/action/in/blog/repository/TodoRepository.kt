package action.`in`.blog.backend.repository

import action.`in`.blog.backend.domain.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository: JpaRepository<TodoEntity, Long> {

}