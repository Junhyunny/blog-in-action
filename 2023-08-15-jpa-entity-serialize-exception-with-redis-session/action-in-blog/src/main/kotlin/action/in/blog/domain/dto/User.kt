package action.`in`.blog.domain.dto

import action.`in`.blog.domain.entity.UserEntity
import org.hibernate.collection.spi.PersistentBag
import java.io.Serializable

data class User(
    val id: Long,
    val name: String,
    val favoritePosts: List<Long>
) : Serializable {

    companion object {

        private const val serialVersionUID: Long = 1L

        fun of(userEntity: UserEntity): User {
            return User(
                id = userEntity.id,
                name = userEntity.name,
                // favoritePosts = userEntity.favoritePosts // Serializable 에러
                favoritePosts = ArrayList(userEntity.favoritePosts) // 정상
            )
        }
    }
}