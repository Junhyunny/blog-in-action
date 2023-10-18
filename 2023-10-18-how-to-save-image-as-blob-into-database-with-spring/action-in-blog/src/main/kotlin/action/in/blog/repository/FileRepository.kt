package action.`in`.blog.repository

import action.`in`.blog.domain.FileEntity
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<FileEntity, Long>