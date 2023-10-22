package action.`in`.blog.controller

import action.`in`.blog.domain.FileEntity
import action.`in`.blog.repository.FileRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/files")
class FileController(
    @Value("\${hostname}")
    private val hostname: String,
    private val fileRepository: FileRepository
) {

    @PostMapping
    fun uploadFiles(file: MultipartFile): String {
        val entity = FileEntity(
            name = UUID.randomUUID().toString(),
            contentType = file.contentType ?: "image/jpeg",
            binaryData = file.bytes,
        )
        fileRepository.save(entity)
        return entity.resourceLocation(hostname)
    }

    @GetMapping("/{id}/images/{name}")
    fun getImage(
        @PathVariable id: Long,
        @PathVariable name: String
    ): ResponseEntity<ByteArrayResource> {
        val result = fileRepository.findById(id).orElseThrow {
            EntityNotFoundException("entity not found when ID is $id")
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(result.contentType))
            .header(HttpHeaders.CACHE_CONTROL, "max-age=2592000")
            .body(ByteArrayResource(result.binaryData))
    }
}