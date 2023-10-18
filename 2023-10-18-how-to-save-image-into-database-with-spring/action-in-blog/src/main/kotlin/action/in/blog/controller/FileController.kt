package action.`in`.blog.controller

import action.`in`.blog.domain.FileEntity
import action.`in`.blog.repository.FileRepository
import jakarta.persistence.EntityNotFoundException
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
    private val fileRepository: FileRepository
) {

    @GetMapping
    fun helloWorld() = "hello"

    @PostMapping
    fun uploadFiles(file: MultipartFile): String {
        val entity = FileEntity(
            name = UUID.randomUUID().toString(),
            contentType = file.contentType ?: "image/jpeg",
            binaryData = file.bytes,
        )
        fileRepository.save(entity)
        return "http://localhost:8080/api/files/${entity.id}/images/${entity.name}"
    }

    @GetMapping("/{id}/images/{name}")
    fun getImage(
        @PathVariable id: Long,
        @PathVariable name: String
    ): ResponseEntity<ByteArrayResource> {
        val result = fileRepository.findById(id).orElseThrow {
            EntityNotFoundException("entity not found when id:${id}")
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(result.contentType))
            .header(HttpHeaders.CACHE_CONTROL, "max-age=2592000")
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${name}\"")
            .body(ByteArrayResource(result.binaryData))
    }
}