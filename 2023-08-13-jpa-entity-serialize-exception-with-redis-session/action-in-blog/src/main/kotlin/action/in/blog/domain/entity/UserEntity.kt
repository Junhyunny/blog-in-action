package action.`in`.blog.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "tb_user")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val name: String,
    @ElementCollection
    @CollectionTable(name = "tb_favorite_posts")
    val favoritePosts: MutableList<Long>
)