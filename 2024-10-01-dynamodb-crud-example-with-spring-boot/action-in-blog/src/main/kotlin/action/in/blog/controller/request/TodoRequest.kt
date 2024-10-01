package action.`in`.blog.controller.request

import action.`in`.blog.repository.entity.TodoEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class TodoRequest(
    val title: String,
    val content: String,
) {
    fun toEntity(): TodoEntity {
        val uuid = UUID.randomUUID()
        val dateTime =
            LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
        val id = "$dateTime-$uuid"
        return TodoEntity(
            pk = "TODO",
            sk = "ID#$id",
            id = id,
            this.title,
            this.content,
        )
    }

    fun toEntity(id: String): TodoEntity =
        TodoEntity(
            pk = "TODO",
            sk = "ID#$id",
            id = id,
            this.title,
            this.content,
        )
}
