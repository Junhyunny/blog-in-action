package action.`in`.blog.controller.response

import action.`in`.blog.repository.entity.TodoEntity

data class TodoResponse(
    val id: String,
    val title: String,
    val content: String
) {
    companion object {
        fun of(entity: TodoEntity) = TodoResponse(
            entity.id,
            entity.title,
            entity.content
        )
    }
}
