package blog.`in`.action.repository

import blog.`in`.action.domain.ReplyEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ReplyRepository : JpaRepository<ReplyEntity, Long> {

    fun findByContentContains(content: String): List<ReplyEntity>
}