package action.`in`.blog.backend.domain

data class Todo(
    val title: String,
) {
    fun toEntity(): TodoEntity = TodoEntity(title = this.title)

    companion object {
        fun from(entity: TodoEntity) = Todo(title = entity.title)
    }
}
