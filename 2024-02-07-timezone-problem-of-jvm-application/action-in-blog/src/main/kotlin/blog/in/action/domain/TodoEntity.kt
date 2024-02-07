package blog.`in`.action.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "TB_TODO")
class TodoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now()
)