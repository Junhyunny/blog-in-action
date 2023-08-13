package action.`in`.blog.service

import action.`in`.blog.domain.dto.User
import action.`in`.blog.repository.UserRepository
import org.springframework.stereotype.Service

interface UserService {
    fun getUser(id: Long): User
}

@Service
class DefaultUserService(
    private val userRepository: UserRepository
) : UserService {
    override fun getUser(id: Long): User {
        val userEntity = userRepository.findById(id).orElseThrow()
        return User.of(userEntity)
    }
}
